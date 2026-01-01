package com.moringaschool.url_qr_shortener.exceptionhandlers;

public class DuplicateResourceException extends RuntimeException {
    private String field;
    private Object value;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String field, Object value) {
        super(String.format("Resource with %s '%s' already exists", field, value));
        this.field = field;
        this.value = value;
    }

    public String getField() { return field; }
    public Object getValue() { return value; }
}
