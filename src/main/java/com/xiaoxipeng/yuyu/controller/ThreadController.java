package com.xiaoxipeng.yuyu.controller;

import com.xiaoxipeng.yuyu.vo.R;
import com.xiaoxipeng.yuyu.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread/")
public class ThreadController {

    @Autowired
    private ThreadService threadServiceImpl;

    /**
     * 测试同步移交队列，正在执行的任务超过核心线程数，最大线程是否会被启用。
     * 会直接启用最大线程
     */
    @RequestMapping("testThread")
    public R<Void> testThread() {
        return R.fromS(threadServiceImpl.test());
    }

    /**
     * 测试submit使用Callable提交任务
     */
    @RequestMapping("testThread-1")
    public R<String> testThread_1() {
        return R.fromS(threadServiceImpl.test_1());
    }

    /**
     * 测试submit使用Runnable提交任务
     */
    @RequestMapping("testThread-2")
    public R<Object> testThread_2() {
        return R.fromS(threadServiceImpl.test_2());
    }

    /**
     *   测试volatile关键字的作用
     *   被 同步移交队列+超过最大线程丢弃任务影响，导致后续任务都是未完成状态，get一直等待。
     *   测试失败++i并不是原子操作，加上volatile关键字后也无效。
     *   volatile关键字作用：1.静止重排序，2.线程可见性。
     */
    @RequestMapping("testVolatile")
    public R<Integer> testVolatile() throws InterruptedException {
        return R.fromS(threadServiceImpl.test_3());
    }

}
