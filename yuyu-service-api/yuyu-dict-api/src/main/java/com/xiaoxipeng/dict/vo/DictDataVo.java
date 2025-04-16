package com.xiaoxipeng.dict.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictDataVo {

    private Long id;

    /**
     * 字典类型ID
     */
    private Long dictTypeId;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 显示顺序
     */
    private Integer dictSort;

    /**
     * CSS样式
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private Integer isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private String status;
}
