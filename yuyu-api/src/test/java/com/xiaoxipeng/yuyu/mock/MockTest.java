package com.xiaoxipeng.yuyu.mock;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoxipeng.api.service.IUserService;
import com.xiaoxipeng.api.service.impl.UserServiceImpl;
import com.xiaoxipeng.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@Slf4j

public class MockTest {

    /**
     * Mock对象     返回mock对象的默认值
     * spy对象      调用真实的方法
     */

    /**
     * Mock/spy对象的方式
     * 1. @ExtendWith(MockitoExtension.class) + @Mock注解方式
     * 2. Mockito.mock(X.class)
     * 3. MockitoAnnotations.openMocks(this) + @Mock注解方式
     */

    @Mock
    UserServiceImpl userService;

    // mock接口和实现类区别 实现类才会执行对应方法
    @Spy
    UserServiceImpl spyUserService;

    @Mock
    IUserService userService2;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test() {
        // 默认返回 null
//        User user = userService.getById(1);

        // 不执行真实的userService.getUserByUsername方法
//        User pxx = userService.getUserByUsername("pxx");

        // 默认返回false
//        boolean b = userService.updateById(new User("pxx", "123"));
    }

    @Test
    public void test2() {
//        Mockito.when(userService.getById(1)).thenReturn(new User("pxx", "123"));

        User user = userService.getById(1);

        User user1 = userService.getById(2);
    }

    @Test
    public void test3() {
//        Mockito.when(userService.getById(ArgumentMatchers.<Integer>any())).thenReturn(new User("pxx", "123"));

        User user = userService.getById(1);

        User user1 = userService.getById(2);
    }

    @Test
    public void test4() {
//        Mockito.when(userService.getById(ArgumentMatchers.<Integer>any())).thenReturn(new User("pxx", "123"));

        User user = userService.getById(1);
        // 期待方法被调用两次，不满足报错
        Mockito.verify(userService, Mockito.times(2)).getById(ArgumentMatchers.<Integer>any());
    }

    @Test
    public void test5() {
//        User pxx = new User("pxx", "123");
//        Mockito.when(userService.getById(ArgumentMatchers.<Integer>any())).thenReturn(pxx);
        User user = userService.getById(2);
        // 断言期待返回值相等
//        Assertions.assertEquals(pxx, user);
    }

    @Test
    public void test6() {
        //表示代码执行，但是不会产生任何副作用，(比如方法里面包含网络请求等)
//        Mockito.doNothing().when(userService).doLogin("pxx", "123");
//        userService.doLogin("pxx", "123");
        // doLogin真实方法不会执行
//        Mockito.doNothing().when(spyUserService).doLogin("pxx", "123");
//        spyUserService.doLogin("pxx", "123");
    }

    @Test
    public void test7() {
//        Mockito.when(userService.getNumber()).thenReturn(97);
//        Mockito.when(spyUserService.getNumber()).thenReturn(98);
//
//        Integer number = userService.getNumber();
//        Integer number1 = spyUserService.getNumber();
    }

    @Test
    public void test8() {
//        Mockito.when(userService.getNumber()).thenThrow(RuntimeException.class);
//
//        try {
//            Integer number = userService.getNumber();
//            Assertions.fail();
//        } catch (Exception e) {
//            Assertions.assertInstanceOf(RuntimeException.class, e);
//        }
    }

    @Test
    public void test9() {
        Mockito.when(userService.count(ArgumentMatchers.any()))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);

//        long count = userService.count(Wrappers.<User>lambdaQuery().eq(Base::getId, 1));
//        long count1 = userService.count(Wrappers.<User>lambdaQuery().eq(Base::getId, 1));
//        long count2 = userService.count(Wrappers.<User>lambdaQuery().eq(Base::getId, 1));
    }

    @Test
    public void test10() {
        Mockito.when(userService.count(ArgumentMatchers.any()))
                .thenAnswer(new Answer<Long>() {

                    @Override
                    public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Object argument = invocationOnMock.getArgument(0);
                        return 0L;
                    }
                });

//        long count = userService.count(Wrappers.<User>lambdaQuery().eq(Base::getId, 1));
//        long count1 = userService.count(Wrappers.<User>lambdaQuery().eq(Base::getId, 1));
//        long count2 = userService.count(Wrappers.<User>lambdaQuery().eq(Base::getId, 1));
    }

    @Test
    public void test11() {
        // mock类可以执行真实方法
//        Mockito.when(userService.getNumber()).thenCallRealMethod();
//        Integer number = userService.getNumber();
    }

    /**
     * 条件
     *  必须要实现类
     * 作用
     *  会收集@Mock @Spy类型注入到UserServiceImpl实现类中的成员变量数据，
     *  构造 --> set --> 反射
     */
    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    public void test12() {
        // mock类可以执行真实方法
//        Mockito.when(userService.getNumber()).thenCallRealMethod();
//        Integer number = userService.getNumber();
    }

}
