package com.hui.api.service.impl.inner;/**
 * 作者:灰爪哇
 * 时间:2024-04-27
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hui.api.mapper.UserMapper;
import com.hui.common.model.entity.User;
import com.hui.common.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 *
 *
 * @author: Hui
 **/
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByAcccessKey(String accessKey) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
