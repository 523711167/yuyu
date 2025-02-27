package com.xiaoxipeng.yuyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaoxipeng.yuyu.mapper")
public class YuyuApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuyuApplication.class, args);
	}

}
