package com.xiaoxipeng.delegate.other;


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
