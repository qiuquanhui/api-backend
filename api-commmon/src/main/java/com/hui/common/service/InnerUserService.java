package com.hui.common.service;


import com.hui.common.model.entity.User;

/**
 * 用户服务
 *
 * @author hui
 */
public interface InnerUserService {

    /**
     * 根据accessKey 查询该用户
     *
     * @param accessKey     用户密钥
     * @return UserVO
     */
    User getUserByAcccessKey(String accessKey);

}
