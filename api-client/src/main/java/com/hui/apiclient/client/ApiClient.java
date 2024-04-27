package com.hui.apiclient.client;/**
 * 作者:灰爪哇
 * 时间:2024-04-17
 */

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hui.apiclient.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.hui.apiclient.utils.SignUtil.getSign;

/**
 *
 *
 * @author: Hui
 **/
public class ApiClient {

    private String accessKey;

    private String secretKey;

    private static final String GATEWAY_HOST = "http://127.0.0.1:8090";


    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public ApiClient(){

    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.get(GATEWAY_HOST + "/api/basic/get", paramMap);

        System.out.println(result);

        return result;
    }

    public String getNameByPost(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.post(GATEWAY_HOST + "/api/basic/post", paramMap);

        System.out.println(result);

        return result;
    }

    public Map<String,String> getHeader(String body){
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
        //一定不要传递secretKey
        //header.put("secretKey", secretKey);
        //参数 2 用户参数
        header.put("body",body);
        //参数 3 sign 签名认证
        if (body == null){
            body = "";
        }
        header.put("sign",getSign(body, secretKey));
        //参数 4 nonce 随机数
        header.put("nonce", RandomUtil.randomString(5));
        //参数 5 timestamp 时间戳
        header.put("timestamp",String.valueOf(System.currentTimeMillis()));

        return header;
    }

    public String getUserNameByPost(User user){

        String json = JSONUtil.toJsonStr(user);
        //获取请求头
        Map<String, String> header = getHeader(json);
        //发起请求
        HttpResponse response = HttpRequest.post(GATEWAY_HOST + "/api/basic/user")
                .addHeaders(header)
                .body(json)
                .execute();

        System.out.println(response.getStatus());

        System.out.println(response.body());

        return response.body();
    }
}
