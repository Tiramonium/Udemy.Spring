package com.udemy.spring.exceptions;

import java.io.Serializable;

public class DefaultException implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long timestamp;
    public Integer status;
    public String error;
    public String exception;
    public String message;
    public String path;

    public DefaultException(Long timestamp, Integer status, String error, String exception, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.exception = exception;
        this.message = message;
        this.path = path;
    }
}