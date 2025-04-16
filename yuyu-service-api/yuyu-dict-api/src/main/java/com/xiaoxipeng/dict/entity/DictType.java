package com.xiaoxipeng.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoxipeng.common.entity.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
@Getter
@Setter
@ToString
@TableName("dict_type")
public class DictType extends Base {

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 节点类型
     */
    private Integer type;

    /**
     * 字典类型编码
     */
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    private String dictTypeName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
