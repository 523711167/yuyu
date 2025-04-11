package com.xiaoxipeng.yuyu.proxytest;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicTest {

    @Test
    public void test() {
        DoCode doCode = new DoCode();
        DoJianzhi doJianzhi = new DoJianzhi(doCode);
        Work o = (Work) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{Work.class}, doJianzhi);
        o.doWork("写代码", 8);
    }
}


interface Work {

    Double doWork(String workName, Integer time);
}

class DoCode implements Work {

    @Override
    public Double doWork(String workName, Integer time) {
        Integer salary = 100;
        System.out.println("开始工作，工作名称：" + workName + "，工作时间：" + time);
        System.out.println("时薪为：" + salary);
        return time * salary * 1.0;
    }
}

class DoJianzhi implements InvocationHandler {

    private Work work;

    public DoJianzhi(Work work) {
        this.work = work;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置内容");
        method.invoke(work, args);
        System.out.println("后置内容");
        return null;
    }
}