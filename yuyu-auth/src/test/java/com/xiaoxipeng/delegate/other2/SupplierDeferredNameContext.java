package com.xiaoxipeng.delegate.other2;

import com.xiaoxipeng.delegate.other.NameContextHolderStrategy;

import java.util.function.Supplier;

public class SupplierDeferredNameContext implements DeferredNameContext {

    private final Supplier<NameContext> supplier;

    private final NameContextHolderStrategy strategy;

    private NameContext nameContext;

    private boolean missingContext;

    public SupplierDeferredNameContext(Supplier<NameContext> supplier, NameContextHolderStrategy strategy) {
        this.supplier = supplier;
        this.strategy = strategy;
    }


    @Override
    public boolean isGenerated() {
        return false;
    }

    @Override
    public DeferredNameContext get() {
        return null;
    }
}
