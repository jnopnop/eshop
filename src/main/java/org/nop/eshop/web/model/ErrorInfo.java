package org.nop.eshop.web.model;

/**
 * Created by nop on 12.12.14.
 */
public class ErrorInfo {
    private String url;
    private String exception;

    public ErrorInfo(String url, Exception e) {
        this.url = url;
        this.exception = e.getLocalizedMessage();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
