package com.hui.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hui.common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author quanhui
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-04-23 17:04:24
* @Entity com.hui.api.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

     List<UserInterfaceInfo> ListTopInvokeInterfaceInfo(int limit);
}




