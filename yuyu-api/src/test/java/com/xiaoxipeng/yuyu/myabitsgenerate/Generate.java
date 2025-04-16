package com.xiaoxipeng.yuyu.myabitsgenerate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.xiaoxipeng.common.entity.Base;


public class Generate {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/yuyu?serverTimezone=UTC&useSSL=false&characterEncoding=utf8",
                        "user",
                        "user")
                .globalConfig(builder -> builder
                        .author("xiaoxipeng")
                        .outputDir("/Users/xixipeng/IdeaProjects/yuyu/yuyu-api/src/main/java/")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.xiaoxipeng.api")
                        .entity("pojo")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addInclude("dict_data", "dict_group", "dict_type")
                        .entityBuilder()
                        .enableLombok()
                        .disableSerialVersionUID()
                        .superClass(Base.class)
                )
                .execute();
    }
}
