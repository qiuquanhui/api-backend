package com.hui.api.service.impl.inner;/**
 * 作者:灰爪哇
 * 时间:2024-04-27
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hui.api.mapper.InterfaceInfoMapper;
import com.hui.common.model.entity.InterfaceInfo;
import com.hui.common.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 *
 *
 * @author: Hui
 **/
@DubboService
public class InnnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo isOKInterfaceInfo(String url, String method) {

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        return  interfaceInfoMapper.selectOne(queryWrapper);
    }
}
