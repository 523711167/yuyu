package com.xiaoxipeng.dict.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
public class SDictDataDto {

    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;

    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
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
