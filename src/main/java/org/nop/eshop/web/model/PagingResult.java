package org.nop.eshop.web.model;

import java.util.Collection;


public class PagingResult<T> {
    private Collection<T> results;
    private PageInfo pageInfo;

    public PagingResult(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
