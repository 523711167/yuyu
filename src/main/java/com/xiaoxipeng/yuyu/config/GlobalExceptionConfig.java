package com.xiaoxipeng.yuyu.config;

import com.xiaoxipeng.yuyu.pojo.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionConfig {

    // 处理自定义异常
    @ExceptionHandler(Exception.class)
    public R<Void> handleCustomException(Exception ex) {
        return R.fail();
    }

}
