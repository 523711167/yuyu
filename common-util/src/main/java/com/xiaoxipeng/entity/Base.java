package com.xiaoxipeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Base {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String creater;

    private LocalDateTime createTime;

    private String updator;

    private LocalDateTime updateTime;
}
