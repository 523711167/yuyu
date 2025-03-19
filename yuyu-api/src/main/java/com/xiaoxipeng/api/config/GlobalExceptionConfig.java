package com.xiaoxipeng.api.config;

import com.xiaoxipeng.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionConfig {

    // 处理自定义异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<Void> handleCustomException(Exception ex) {
        log.error("An error occurred: ", ex);
        return R.fail();
    }

}
