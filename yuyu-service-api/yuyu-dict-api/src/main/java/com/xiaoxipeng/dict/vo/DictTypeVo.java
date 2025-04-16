package com.xiaoxipeng.dict.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictTypeVo {

    private Long id;
    /**
     * 字典类型编码
     */
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    private String dictTypeName;

    /**
     * 字典分组ID
     */
    private Long dictGroupId;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;
}
