package org.nop.eshop.web.model;


import java.util.Collection;

public class PagerResult<T> {
    public static final Integer PAGE_SIZE = 20;
    public static final String DEFAULT_SORTING_FIELD = "id";

    private Collection<T> results;

    private int currPage;
    private int lastPage;
    private long maxResults = -1;
    private int pageSize;
    private boolean ASK = true;
    private String sortingField = DEFAULT_SORTING_FIELD;

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

    public static Integer getPageSize() {
        return PAGE_SIZE;
    }

    public boolean isASK() {
        return ASK;
    }

    public void setASK(boolean ASK) {
        this.ASK = ASK;
    }

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }
}
