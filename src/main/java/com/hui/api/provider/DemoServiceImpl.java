package com.hui.api.provider;/**
 * 作者:灰爪哇
 * 时间:2024-04-27
 */

import org.apache.dubbo.config.annotation.DubboService;

/**
 *
 *
 * @author: Hui
 **/
@DubboService
public class DemoServiceImpl implements DemoService{
    @Override
    public String sayHello(String name) {

        System.out.println(" 远程调用成功 ");

        return "Hello " + name;
    }
}
