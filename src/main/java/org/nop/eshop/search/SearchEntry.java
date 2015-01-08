package org.nop.eshop.search;


public class SearchEntry<T> {
    private String fieldName;
    private T value;
    private T value2;
    private T[] values;
    private boolean isRange = false;
    private boolean strictlyLimited = false;
    private boolean onlyLowBounded = false;
    private boolean onlyHighBounded =  false;
    private boolean isMultiValue = false;

    public SearchEntry(String fieldName, T value) {
        this(fieldName, value, null, false, null);
    }

    public SearchEntry(String fieldName, T value, T value2) {
        this(fieldName, value, value2, true, null);
        if (value != null && value2 != null) {
            this.strictlyLimited = true;
        } else if (value == null && value2 != null) {
            this.onlyHighBounded = true;
        } else if (value != null) {
            this.onlyLowBounded = true;
        }
    }

    public SearchEntry(String fieldName, T[] values) {
        this(fieldName, null, null, false, values);
        this.isMultiValue = true;
    }

    private SearchEntry(String fieldName, T value, T value2, boolean isRange, T[] values) {
        this.fieldName = fieldName;
        this.value = value;
        this.value2 = value2;
        this.isRange = isRange;
        this.values = values;
    }

    public String getFieldName() {
        return fieldName;
    }

    public T getValue() {
        return value;
    }

    public T getValue2() {
        return value2;
    }

    public boolean isRange() {
        return isRange;
    }

    public boolean isStrictlyLimited() {
        return strictlyLimited;
    }

    public boolean isOnlyLowBounded() {
        return onlyLowBounded;
    }

    public boolean isOnlyHighBounded() {
        return onlyHighBounded;
    }

    public T[] getValues() {
        return values;
    }

    public boolean isMultiValue() {
        return isMultiValue;
    }
}
