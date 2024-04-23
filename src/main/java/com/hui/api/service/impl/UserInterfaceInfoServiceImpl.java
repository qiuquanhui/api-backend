package com.hui.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hui.api.common.ErrorCode;
import com.hui.api.exception.BusinessException;
import com.hui.api.mapper.UserInterfaceInfoMapper;
import com.hui.api.model.entity.UserInterfaceInfo;
import com.hui.api.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 邱权辉
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2024-04-23 17:04:24
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {

        if (userInterfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long id = userInterfaceInfo.getId();
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        Integer status = userInterfaceInfo.getStatus();
        Date createTime = userInterfaceInfo.getCreateTime();
        Date updateTime = userInterfaceInfo.getUpdateTime();
        Integer isDelete = userInterfaceInfo.getIsDelete();


        // 创建时，所有参数必须非空
        if (add) {
            if (interfaceInfoId == 0 || interfaceInfoId < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"不存在该接口");
            }
            if (userId == 0 || userId < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"不存在该用户");
            }
        }
    }

    @Override
    public boolean invokeCount(Long interfaceInfoId, Long UserId) {
        //1.校验参数
        if (interfaceInfoId == null || UserId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.查询出该数据
        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
        userInterfaceInfoQueryWrapper.eq("interfaceInfoId",interfaceInfoId);
        userInterfaceInfoQueryWrapper.eq("userId",UserId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(userInterfaceInfoQueryWrapper);
        if (userInterfaceInfo == null){
            throw new BusinessException(ErrorCode .PARAMS_ERROR,"找不到改数据");
        }
        //3.更新数据
        userInterfaceInfo.setTotalNum(userInterfaceInfo.getTotalNum() + 1);
        userInterfaceInfo.setLeftNum(userInterfaceInfo.getLeftNum() - 1);
        boolean result = this.updateById(userInterfaceInfo);

        return result;
    }
}




