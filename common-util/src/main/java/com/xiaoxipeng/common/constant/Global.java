package com.xiaoxipeng.common.constant;

public class Global {

    public static final Integer TRUE = 1;

    public static final Integer FALSE = 0;

    public static final String MY_BLOOM_FILTER = "xiaoxipeng:yuyu-api:bloomFilter";

    /**
     * 逻辑过期+分布式锁解决缓存穿透的问题
     */
    public static final String MY_EXPIRE_KEY = "xiaoxipeng:yuyu-api:expireKey";


    public static final String MY_LOCK_EXPIRE_KEY = "xiaoxipeng:yuyu-api:lockExpireKey";

    public static final String MY_LOCK_EXPIRE_SPIN_KEY = "xiaoxipeng:yuyu-api:lockExpireKey";

    /**
     * 分布式锁解决缓存穿透的问题
     */
    public static final String MY_LOCK_KEY = "xiaoxipeng:yuyu-api:lockKey";

    public static final String MY_HOT_KEY = "xiaoxipeng:yuyu-api:hotKey";
}
