package com.hui.apigateway;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hui.apiclient.utils.SignUtil.getSign;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    //白名单
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//        1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String id = request.getId();
        log.info("请求标识, id: {}", id);
        log.info("请求路径, path: {}", request.getPath()); ///api/basic/get
        log.info("请求方法, method: {}", request.getMethod());
        log.info("请求参数, params: {}", request.getQueryParams());// {name=[hui]}
        String hostName = request.getLocalAddress().getHostString();
        log.info("请求主机名, hostName: {}", hostName);
        log.info("请求本地地址,localAddress：{}",request.getLocalAddress()); //127.0.0.1:8090
        log.info("请求远程地址,remoteAddress：{}",request.getRemoteAddress());//127.0.0.1:58610
        ServerHttpResponse response = exchange.getResponse();
//        2. （黑白名单）
        if (!IP_WHITE_LIST.contains(hostName)){
            return handlerNoAuth(response);
        }
//        3. 用户鉴权（判断 ak、sk 是否合法等等）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp"); //时间戳,转递以秒为单位

        //服务端签名认证校验
        //todo 参数1 实际中从数据库中查询是否有该数据
        if (!"hui".equals(accessKey)){
            return handlerNoAuth(response);
        }

        //todo 随机数怎么校验：后端传递随机数给前端，查询数据校验，判断该随机数是否使用过。
        if (nonce.length() > 1000){
            return handlerNoAuth(response);
        }

        //参数 3  校验sign
        String secretKey = "abcdefg";  //todo 实际中从数据库中查询
        String serverSign = getSign(body, secretKey);
        if (!serverSign.equals(sign)){
            return handlerNoAuth(response);
        }

        //时间戳校验,五分钟以后的就不可以调用 1秒等于1000毫秒
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long FIVE_MINUTE = 5 * 60L;
        if (currentTimeMillis - Long.parseLong(timestamp) > FIVE_MINUTE ){
            return handlerNoAuth(response);
        }

//        5-6会先执行才会到handlerResponse中执行7-9
//        5. todo 实际从数据库中查询 请求的模拟接口是否存在？
//        6. todo  请求转发，调用模拟接口
        // 以下操作都在响应日志中调用
//        7. 响应日志
//        8. todo  调用成功，接口调用次数 + 1
//        9. 调用失败，返回一个规范的错误码
        return handlerResponse(exchange,chain);
    }

    public Mono<Void> handlerResponse(ServerWebExchange exchange, GatewayFilterChain chain){
        try {
            //从交换机中拿到响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            //从响应对象中拿到缓存工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //获取装饰器响应对象
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                //调用模拟成功之后才会调用这个方法
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);

                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            //        7. todo  调用成功，接口调用次数 + 1

                            // 合并多个流集合，解决返回体分段传输
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer buff = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[buff.readableByteCount()];
                            buff.read(content);
                            DataBufferUtils.release(buff);//释放掉内存

                            // 构建返回日志
                            String data = new String(content);
                            List<Object> rspArgs = new ArrayList<>();
                            rspArgs.add(originalResponse.getStatusCode().value());
                            rspArgs.add(exchange.getRequest().getURI());
                            log.info("<-- {} {}\n{}", rspArgs.toArray());
                            //        8. 响应日志
                            log.info("响应内容： " + data);
                            return bufferFactory.wrap(content);
                        }));
                    } else {
                        //        9. 调用失败，返回一个规范的错误码
                        log.error("<-- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            //设置响应对象为装饰过的对象
            return chain.filter(exchange.mutate().response(decoratedResponse).build());

        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    private Mono<Void> handlerNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}