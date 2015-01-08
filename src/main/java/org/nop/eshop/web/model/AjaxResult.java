package org.nop.eshop.web.model;


public class AjaxResult {
    private Object data;
    private Boolean success = true;

    public AjaxResult() {
    }

    public AjaxResult(Boolean success) {
        this(null, success);
    }

    public AjaxResult(Object data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
