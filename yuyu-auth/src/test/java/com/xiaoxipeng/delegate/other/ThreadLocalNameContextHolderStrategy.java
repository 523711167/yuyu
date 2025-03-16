package com.xiaoxipeng.delegate.other;


import com.xiaoxipeng.delegate.other2.NameContext;
import org.springframework.util.Assert;

import java.util.function.Supplier;

public class ThreadLocalNameContextHolderStrategy implements NameContextHolderStrategy {

    private static final ThreadLocal<Supplier<NameContext>> contextHolder = new ThreadLocal<>();

    @Override
    public void setContext(NameContext context) {
        contextHolder.set(() -> context);
    }

    @Override
    public void setDeferredContext(Supplier<NameContext> deferredContext) {
        Supplier<NameContext> notNullDeferredContext = () -> {
            NameContext result = deferredContext.get();
            Assert.notNull(result, "A Supplier<SecurityContext> returned null and is not allowed.");
            return result;
        };
        contextHolder.set(notNullDeferredContext);
    }

    @Override
    public NameContext getContext() {
        return getDeferredContext().get();
    }

    @Override
    public Supplier<NameContext> getDeferredContext() {
        Supplier<NameContext> result = contextHolder.get();
        if (result == null) {
            NameContext context = createEmptyContext();
            result = () -> context;
            contextHolder.set(result);
        }
        return result;
    }



    @Override
    public NameContext createEmptyContext() {
        return null;
    }


}
