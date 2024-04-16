package com.hui.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hui.api.common.ErrorCode;
import com.hui.api.exception.BusinessException;
import com.hui.api.mapper.InterfaceInfoMapper;
import com.hui.api.model.entity.InterfaceInfo;
import com.hui.api.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 邱权辉
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-04-16 16:55:02
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo InterfaceInfo, boolean add) {


        if (InterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long id = InterfaceInfo.getId();
        String name = InterfaceInfo.getName();
        String description = InterfaceInfo.getDescription();
        String url = InterfaceInfo.getUrl();
        String method = InterfaceInfo.getMethod();
        String requestHeader = InterfaceInfo.getRequestHeader();
        String responseHeader = InterfaceInfo.getResponseHeader();
        Integer status = InterfaceInfo.getStatus();
        Long userId = InterfaceInfo.getUserId();
        Date createTime = InterfaceInfo.getCreateTime();
        Date updateTime = InterfaceInfo.getUpdateTime();
        Integer isDelete = InterfaceInfo.getIsDelete();

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, description, url, method)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }
}




