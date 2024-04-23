package com.hui.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hui.api.model.entity.InterfaceInfo;

/**
* @author 邱权辉
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-04-16 16:55:02
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param InterfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo InterfaceInfo, boolean add);
}
