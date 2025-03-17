package com.xiaoxipeng.delegate.other;

/**
 * Holder 持有 Strategy get或set 上下文
 * 上下文就是容器
 */
public class NameContextHolder {

    private static NameContextHolderStrategy strategy;

    static {
        initialize();
    }


    private static void initialize() {
        initializeStrategy();
    }

    private static void initializeStrategy() {
        strategy = new ThreadLocalNameContextHolderStrategy();
    }


    public static NameContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }
}
