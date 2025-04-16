package com.xiaoxipeng.dict.dto;

import jakarta.validation.constraints.NotBlank;
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
public class UDictDataDto {

    /**
     * 字典类型ID
     */
    @NotBlank(message = "主键ID不能为空")
    private Long id;

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

}
