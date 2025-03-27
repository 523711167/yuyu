package com.xiaoxipeng.api.service.impl;

import com.xiaoxipeng.api.service.ThreadService;
import com.xiaoxipeng.common.vo.S;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class ThreadServiceImpl implements ThreadService {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private volatile int num = 0;
//    private int num = 0;

    @Override
    public S<Void> test() {
        threadPoolExecutor.execute(() -> {
            log.info(String.format("当前线程执行名称 ====> %s", Thread.currentThread().getName()));
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error("异常中断发生", e);
            }
        });
        return S.success();
    }

    @Override
    public S<String> test_1() {
        Future<String> submit = threadPoolExecutor.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(3000);
                return "Callable提交任务";
            }

        });

        String result = null;
        try {
            result = submit.get();
        } catch (InterruptedException e) {
            log.error("异常中断发生", e);
        } catch (ExecutionException e) {
            log.error("异常发生", e);
        }
        return S.success(result);
    }

    @Override
    public S<Object> test_2() {
        Future<?> submit = threadPoolExecutor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Runnable ====> 输出打印");
            }

        });

        Object o = null;
        try {
            o = submit.get();
        } catch (InterruptedException e) {
            log.error("异常中断发生", e);
        } catch (ExecutionException e) {
            log.error("异常发生", e);
        }

        return S.success(o);
    }

    @Override
    public S<Integer> test_3() throws InterruptedException {
        List<Future<Integer>> container = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 1000; i++) {
            Future<Integer> submit = threadPoolExecutor.submit(new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    return ++num;
                }

            });

            container.add(submit);
        }

        container.forEach(submit -> {
            try {
                Integer i = submit.get();
            } catch (InterruptedException e) {
                log.error("异常中断发生", e);
            } catch (ExecutionException e) {
                log.error("异常发生", e);
            }
        });

        return S.success(num);
    }
}
