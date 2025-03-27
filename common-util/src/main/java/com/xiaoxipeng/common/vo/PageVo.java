package com.xiaoxipeng.common.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageVo<T> {

    /**
     * 当前页
     */
    private Long current;

    /**
     * 每页显示条数
     */
    private Long size;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 数据
     */
    private List<T> data;


}
