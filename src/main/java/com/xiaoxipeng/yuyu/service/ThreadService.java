package com.xiaoxipeng.yuyu.service;

import com.xiaoxipeng.yuyu.pojo.S;

public interface ThreadService {


    /**
     * 测试同步移交队列，超过核心线程数任务
     * @return
     */
    S<Void> test();
}
