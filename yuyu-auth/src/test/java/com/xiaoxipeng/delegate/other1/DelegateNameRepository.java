package com.xiaoxipeng.delegate.other1;

import com.xiaoxipeng.delegate.other2.DeferredNameContext;
import com.xiaoxipeng.delegate.other2.NameContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class DelegateNameRepository implements NameRepository {

    List<NameRepository> delegates = new ArrayList<>();


    @Override
    public NameContext loadContext(HttpServletRequest request) {
        NameContext nameContext = null;
        for (NameRepository delegate : delegates) {
            nameContext = delegate.loadContext(request);
        }
        return nameContext;
    }

    @Override
    public DeferredNameContext loadDeferredContext(HttpServletRequest request) {
        DeferredNameContext deferredNameContext = null;
        for (NameRepository delegate : this.delegates) {
            if (deferredNameContext == null) {
                deferredNameContext = delegate.loadDeferredContext(request);
            }
            else {
                DeferredNameContext next = delegate.loadDeferredContext(request);
            }
        }
        return deferredNameContext;
    }
}
