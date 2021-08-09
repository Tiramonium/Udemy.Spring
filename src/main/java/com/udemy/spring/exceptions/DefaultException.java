package com.udemy.spring.exceptions;

import java.io.Serializable;

public class DefaultException implements Serializable {
     private static final long serialVersionUID = 1L;

     public Integer Status;
     public String Message;
     public Long Timestamp;

     public DefaultException(Integer status, String message, Long timestamp) {
         this.Status = status;
         this.Message = message;
         this.Timestamp = timestamp;
     }
}