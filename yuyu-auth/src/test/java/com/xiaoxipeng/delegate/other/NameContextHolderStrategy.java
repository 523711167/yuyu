package com.xiaoxipeng.delegate.other;


import com.xiaoxipeng.delegate.other2.NameContext;

import java.util.function.Supplier;

public interface NameContextHolderStrategy {

    NameContext getContext();


    default Supplier<NameContext> getDeferredContext() {
        return this::getContext;
    }


    void setContext(NameContext context);


    default void setDeferredContext(Supplier<NameContext> deferredContext) {
        setContext(deferredContext.get());
    }


    NameContext createEmptyContext();
}
