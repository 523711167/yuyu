package com.xiaoxipeng.api.controller;

import com.xiaoxipeng.api.service.IBloomService;
import com.xiaoxipeng.api.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloom/")
public class BloomController {

    @Autowired
    private IBloomService bloomServiceImpl;


    @RequestMapping("createBloom")
    public R<Void> createBloom() {
        bloomServiceImpl.createBloom();
        return R.success();
    }

    @RequestMapping("testBloom")
    public R<Void> testBloom() {
        bloomServiceImpl.testBloom();
        return R.success();
    }

    @RequestMapping("testContain")
    public R<Void> testContain() {
        bloomServiceImpl.testContain();
        return R.success();
    }


}
