package com.xiaoxipeng.dict.vo;

import com.xiaoxipeng.common.vo.TreeVo;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DictTypeTreeVo extends TreeVo<DictTypeTreeVo> {

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


}
