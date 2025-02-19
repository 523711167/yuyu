package com.xiaoxipeng.yuyu.controller;

import com.xiaoxipeng.yuyu.pojo.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello/")
public class HelloController {

    @RequestMapping("xiaoxipeng")
    public R<Void> testThread() {
        return R.success();
    }
}
