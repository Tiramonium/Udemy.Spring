package com.udemy.spring.exceptions;

public class ValidationExceptionField {
    public String fieldName;
    public String message;

    public ValidationExceptionField() {
    }

    public ValidationExceptionField(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}