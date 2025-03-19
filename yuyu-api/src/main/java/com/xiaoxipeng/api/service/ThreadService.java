package com.xiaoxipeng.api.service;


import com.xiaoxipeng.vo.S;

public interface ThreadService {

    S<Void> test();

    S<String> test_1();

    S<Object> test_2();

    S<Integer> test_3() throws InterruptedException;
}
