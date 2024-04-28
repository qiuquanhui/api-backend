package com.hui.api.model.vo;

import com.hui.common.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子视图
 *
 * @author hui
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoVO extends InterfaceInfo {

    /**
     * 接口调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}