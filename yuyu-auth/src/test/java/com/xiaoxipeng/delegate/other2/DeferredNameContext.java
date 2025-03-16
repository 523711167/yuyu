package com.xiaoxipeng.delegate.other2;

import java.util.function.Supplier;

public interface DeferredNameContext extends Supplier<DeferredNameContext> {

    boolean isGenerated();
}
