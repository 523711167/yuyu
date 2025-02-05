package com.xiaoxipeng.yuyu.controller;

import com.xiaoxipeng.yuyu.pojo.R;
import com.xiaoxipeng.yuyu.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread/")
public class ThreadController {

    @Autowired
    private ThreadService threadServiceImpl;

    @RequestMapping("testThread")
    public R<Void> testThread() {
        return R.fromS(threadServiceImpl.test());
    }
}
