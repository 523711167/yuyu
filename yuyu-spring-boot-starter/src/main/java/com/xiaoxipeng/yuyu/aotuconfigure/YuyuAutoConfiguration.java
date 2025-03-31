package com.xiaoxipeng.yuyu.aotuconfigure;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 不能被springboot通过包扫描扫到
 */
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
