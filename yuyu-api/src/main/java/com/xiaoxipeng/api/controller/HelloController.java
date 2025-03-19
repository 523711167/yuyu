package com.xiaoxipeng.api.controller;

import com.xiaoxipeng.vo.R;
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
