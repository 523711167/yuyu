package com.xiaoxipeng.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoxipeng.common.entity.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 字典分组表
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
@Getter
@Setter
@ToString
@TableName("dict_group")
public class DictGroup extends Base {

    /**
     * 分组编码
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
