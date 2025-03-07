package com.xiaoxipeng.api.controller;

import com.xiaoxipeng.api.pojo.User;
import com.xiaoxipeng.api.service.IUserService;
import com.xiaoxipeng.api.vo.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService userServiceImpl;

    @RequestMapping("list")
    public R<List<User>> list() {
        return R.success(userServiceImpl.list());
    }
}
