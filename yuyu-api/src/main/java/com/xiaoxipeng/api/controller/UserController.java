package com.xiaoxipeng.api.controller;

import com.xiaoxipeng.api.service.IUserService;
import com.xiaoxipeng.user.dto.UserPageDto;
import com.xiaoxipeng.user.vo.UserPageVo;
import com.xiaoxipeng.user.vo.UserVo;
import com.xiaoxipeng.common.vo.PageVo;
import com.xiaoxipeng.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.xiaoxipeng.user.feign.UserClient.USER_API_PREFIX;


/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-03-25
 */
@RestController
@RequestMapping(USER_API_PREFIX)
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("username")
    public R<UserVo> getUserByUsername(@RequestParam(required = true) String username) {
        return R.fromS(userService.getUserByUsername(username));
    }

    @PostMapping("page")
    public R<PageVo<UserPageVo>> page(@RequestBody UserPageDto userPageDto) {
        return R.fromS(userService.listPage(userPageDto));
    }

}
