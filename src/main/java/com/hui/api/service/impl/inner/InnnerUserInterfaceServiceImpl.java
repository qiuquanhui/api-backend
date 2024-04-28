package com.hui.api.service.impl.inner;/**
 * 作者:灰爪哇
 * 时间:2024-04-27
 */

import com.hui.api.service.UserInterfaceInfoService;
import com.hui.common.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 *
 *
 * @author: Hui
 **/
@DubboService
public class InnnerUserInterfaceServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(Long interfaceInfoId, Long UserId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,UserId);
    }
}
