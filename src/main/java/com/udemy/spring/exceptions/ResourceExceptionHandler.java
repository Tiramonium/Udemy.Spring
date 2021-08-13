package com.udemy.spring.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<DefaultException> ObjectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        DefaultException exception = new DefaultException(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Object Not Found", ObjectNotFoundException.class.getName(), ex.getMessage(), request.getServletPath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<DefaultException> DataIntegrityViolation(DataIntegrityException ex,
            HttpServletRequest request) {
        DefaultException exception = new DefaultException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Data Integrity Violation", DataIntegrityException.class.getName(), ex.getMessage(),
                request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationException> ModelNotValid(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<ValidationExceptionField> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationExceptionField(error.getField(), error.getDefaultMessage()));
        }

        ValidationException exception = new ValidationException(System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(), "Model Not Valid", ValidationException.class.getName(),
                String.format("Modelo inválido! Tipo: %s", ex.getParameter().getParameterType().getName()), request.getServletPath(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}