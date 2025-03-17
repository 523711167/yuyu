package com.xiaoxipeng.delegate;

import com.xiaoxipeng.delegate.other.NameContextHolder;
import com.xiaoxipeng.delegate.other1.DelegateNameRepository;
import com.xiaoxipeng.delegate.other2.DeferredNameContext;
import com.xiaoxipeng.delegate.other2.NameContext;

public interface Name {

    public static void main(String[] args) {
//        DelegateNameRepository delegateNameRepository = new DelegateNameRepository();
//        NameContext nameContext = delegateNameRepository.loadContext(null);
//        NameContextHolder.getContextHolderStrategy().setContext(nameContext);

        DelegateNameRepository delegateNameRepository = new DelegateNameRepository();
        DeferredNameContext deferredNameContext = delegateNameRepository.loadDeferredContext(null);


    }
}
