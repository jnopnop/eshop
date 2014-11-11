package org.nop.eshop.web.model;


import java.util.Collection;

public class PagerResult<T> {
    public static final Integer PAGE_SIZE = 20;
    public static final String BACK = "here";
    public static final String FORTH = "there";

    private Collection<T> results;
    private int currPage;
    private int lastPage;

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
}
