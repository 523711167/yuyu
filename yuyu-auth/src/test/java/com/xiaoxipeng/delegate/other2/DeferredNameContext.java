package com.xiaoxipeng.delegate.other2;

import java.util.function.Supplier;

/**
 * get容器的容器
 */
public interface DeferredNameContext extends Supplier<DeferredNameContext> {

    boolean isGenerated();
}
