package com.xiaoxipeng.yuyu.service.impl;

import com.xiaoxipeng.yuyu.pojo.S;
import com.xiaoxipeng.yuyu.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ThreadServiceImpl implements ThreadService {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public S<Void> test() {
        threadPoolExecutor.execute(() -> {
            log.info(String.format("当前线程执行名称 ====> %s", Thread.currentThread().getName()));
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        threadPoolExecutor.submit()
        return S.success();
    }
}
