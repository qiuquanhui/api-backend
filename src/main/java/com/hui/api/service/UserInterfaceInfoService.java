package com.hui.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hui.common.model.entity.UserInterfaceInfo;

/**
* @author quanhui
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-04-23 17:04:24
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验
     *
     * @param userInterfaceInfo
     * @param add 是否为创建校验
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);


    /**
     * 调用之后执行修改接口次数
     *
     * @param
     * @param
     */
    boolean invokeCount(Long interfaceInfoId, Long UserId);
}
