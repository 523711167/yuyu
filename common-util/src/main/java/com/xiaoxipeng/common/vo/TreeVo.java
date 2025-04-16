package com.xiaoxipeng.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TreeVo<T> {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 类型
     */
    private Integer type;

    private List<T> children;
}
