package com.xiaoxipeng.delegate.other2;

public class DelegatingDeferredNameContext implements DeferredNameContext {

    private final DeferredNameContext previous;

    private final DeferredNameContext next;

    DelegatingDeferredNameContext(DeferredNameContext previous, DeferredNameContext next) {
        this.previous = previous;
        this.next = next;
    }

    @Override
    public DeferredNameContext get() {
        DeferredNameContext deferredNameContext = this.previous.get();
        if (!this.previous.isGenerated()) {
            return deferredNameContext;
        }
        return this.next.get();
    }

    @Override
    public boolean isGenerated() {
        return false;
    }
}
