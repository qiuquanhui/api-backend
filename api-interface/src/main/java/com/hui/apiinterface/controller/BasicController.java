/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hui.apiinterface.controller;

import com.hui.apiclient.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.hui.apiclient.utils.SignUtil.getSign;


/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/get")
    public String getnameByGet(String name, HttpServletRequest request) {
        String source= request.getHeader("source");
        System.out.println(source);
        if (!source.equals("hui")){
            throw new RuntimeException("用户无权限");
        }
        return "GET 方法，用户的名称是： " + name;
    }

    @PostMapping("/post")
    public String getnameByPost(@RequestParam String name) {
        return "POST 方法，用户的名称是： " + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {

        String accessKey = request.getHeader("accessKey");
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String secretKey = "abcdefg"; //todo 数据上从数据库查询

        //服务端签名认证校验
        //todo 参数1 从数据库中查询是否有该数据
        if (!accessKey.equals("hui")){
            throw new RuntimeException("无权限");
        }

        //todo 随机数怎么校验：后端传递随机数给前端，查询数据校验，判断该随机数是否使用过。
        if (nonce.length() > 1000){
            throw new RuntimeException("无权限");
        }

        //参数 3  校验sign
        String serverSign = getSign(body, secretKey);
        if (!serverSign.equals(sign)){
            throw new RuntimeException("无权限");
        }

        //时间戳校验,五分钟以后的就不可以调用 1秒等于1000毫秒
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > 300000 ){
            throw new RuntimeException("无权限");
        }


        return "POST 方法,实体类用户的名称为： " + user.getName();

    }

}
