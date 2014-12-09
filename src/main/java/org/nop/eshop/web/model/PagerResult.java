package org.nop.eshop.web.model;


import java.util.Collection;

public class PagerResult<T> {
    public static final Integer PAGE_SIZE = 20;

    private Collection<T> results;
    private int currPage;
    private int lastPage;
    private long maxResults = -1;

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public long getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(long maxResults) {
        this.maxResults = maxResults;
    }
}
