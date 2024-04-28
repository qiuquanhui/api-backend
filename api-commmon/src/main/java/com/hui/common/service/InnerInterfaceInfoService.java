package com.hui.common.service;

import com.hui.common.model.entity.InterfaceInfo;

/**
* @author quanhui
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-04-16 16:55:02
*/
public interface InnerInterfaceInfoService  {

    /**
     * 查询该方法是否可以调用
     *
     * @param Path
     * @param method
     * @Return Boolean
     */
    InterfaceInfo isOKInterfaceInfo(String Path, String method);
}
