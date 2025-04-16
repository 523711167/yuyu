package com.xiaoxipeng.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoxipeng.common.entity.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
@Getter
@Setter
@ToString
@TableName("dict_data")
public class DictData extends Base {

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
