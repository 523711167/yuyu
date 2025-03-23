package com.xiaoxipeng.yuyu.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SpyMockTest {


    @Test
    public void test() {
        List<Object> arr = new ArrayList<>();
        List<Object> spy = Mockito.spy(arr);
        Mockito.when(spy.get(0)).thenReturn(100);
//        Mockito.doReturn("foo").when(spy).get(0);
        System.out.println(spy.get(0));
        spy.add("one");
        spy.add("o2ne");
        System.out.println(spy.get(0));
        System.out.println(spy.size());

    }
}
