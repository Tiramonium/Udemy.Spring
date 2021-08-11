package com.udemy.spring.exceptions;

import java.io.Serializable;

public class DefaultException implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long Timestamp;
    public Integer Status;
    public String Error;
    public String Exception;
    public String Message;
    public String Path;

    public DefaultException(Long timestamp, Integer status, String error, String exception, String message, String path) {
        this.Timestamp = timestamp;
        this.Status = status;
        this.Error = error;
        this.Exception = exception;
        this.Message = message;
        this.Path = path;
    }
}