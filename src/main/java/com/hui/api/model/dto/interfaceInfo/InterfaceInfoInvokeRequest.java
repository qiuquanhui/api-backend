package com.hui.api.model.dto.interfaceInfo;/**
 * 作者:灰爪哇
 * 时间:2024-04-23
 */

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author: Hui
 **/
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 接口参数
     */
    private String userRequestParams;

    private static final Long serialVersionUID = 1L;

}
