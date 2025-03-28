package com.xiaoxipeng.yuyu.aotuconfigure;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(
        {
                com.xiaoxipeng.yuyu.aotuconfigure.config.ObjectMapperConfig.class
        }
)
public class YuyuAutoConfiguration {


        public YuyuAutoConfiguration() {
                System.out.println("YuyuAutoConfiguration");
        }
}
