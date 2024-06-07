package com.hui.api.controller;/**
 * 作者:灰爪哇
 * 时间:2024-04-27
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hui.api.annotation.AuthCheck;
import com.hui.api.common.BaseResponse;
import com.hui.api.common.ResultUtils;
import com.hui.api.mapper.InterfaceInfoMapper;
import com.hui.api.mapper.UserInterfaceInfoMapper;
import com.hui.api.model.vo.UserInterfaceInfoVO;
import com.hui.common.model.entity.InterfaceInfo;
import com.hui.common.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Hui
 **/
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;


    @GetMapping("top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<UserInterfaceInfoVO>> getTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.ListTopInvokeInterfaceInfo(3);
        //查询出userInterface 调用最高的3个接口
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        //根据id查询出相应的接口信息。
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.selectList(interfaceInfoQueryWrapper);
        List<UserInterfaceInfoVO> userInterfaceInfoVOS = interfaceInfos.stream().map(interfaceInfo -> {
            UserInterfaceInfoVO userInterfaceInfoVO = new UserInterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, userInterfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            userInterfaceInfoVO.setTotalNum(totalNum);
            return userInterfaceInfoVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(userInterfaceInfoVOS);

    }
}
