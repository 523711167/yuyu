package com.xiaoxipeng.yuyu.service;

import com.xiaoxipeng.yuyu.pojo.S;

public interface ThreadService {

    S<Void> test();

    S<String> test_1();

    S<Object> test_2();

    S<Integer> test_3() throws InterruptedException;
}
