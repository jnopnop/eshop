package org.nop.eshop.web.model;

import java.util.Map;

/**
 * Created by nop on 16/10/14.
 */
public class SimpleModel {
    private Long id;
    private String name;
    private Map<Long, String> params;

    public Map<Long, String> getParams() {
        return params;
    }

    public void setParams(Map<Long, String> params) {
        this.params = params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
