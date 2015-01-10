package org.nop.eshop.service;


import org.nop.eshop.web.model.PagerResult;

public interface PagingService {
    PagerResult<?> build();
}
