package com.xiaoxipeng.yuyu.myabitsgenerate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.xiaoxipeng.api.pojo.Base;

import java.nio.file.Paths;

public class Generate {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.18.124:3306/yuyu?serverTimezone=UTC&useSSL=false&characterEncoding=utf8",
                        "root",
                        "123456")
                .globalConfig(builder -> builder
                        .author("xiaoxipeng")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.xiaoxipeng.yuyu")
                        .entity("pojo")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addTablePrefix("tb_")
                        .addInclude("tb_user")
                        .entityBuilder()
                        .enableLombok()
                        .disableSerialVersionUID()
                        .superClass(Base.class)
                )
                .execute();
    }
}
