package com.udemy.spring.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends DefaultException {
    public List<ValidationExceptionField> errors = new ArrayList<>();;

    public ValidationException(Long timestamp, Integer status, String error, String exception, String message, String path, List<ValidationExceptionField> errors) {
        super(timestamp, status, error, exception, message, path);
        this.errors = errors;
    }
}