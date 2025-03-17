package com.xiaoxipeng.delegate.other1;

import com.xiaoxipeng.delegate.other2.DeferredNameContext;
import com.xiaoxipeng.delegate.other2.NameContext;
import com.xiaoxipeng.delegate.other2.SupplierDeferredNameContext;
import com.xiaoxipeng.delegate.other.NameContextHolder;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Supplier;

/**
 *  Repository 产出 上下文
 */
public interface NameRepository {

    NameContext loadContext(HttpServletRequest request);


    default DeferredNameContext loadDeferredContext(HttpServletRequest request) {
        Supplier<NameContext> supplier = () -> loadContext(request);
        return new SupplierDeferredNameContext(supplier,
                NameContextHolder.getContextHolderStrategy());
    }


}
