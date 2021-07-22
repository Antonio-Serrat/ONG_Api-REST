package com.alkemy.ong.exception;

import com.alkemy.ong.util.CustomFieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import javax.resource.spi.InvalidPropertyException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class AppExceptionsHandler {

    //Excepciones al validar un modelo en el controller
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleArgumentNotValid(MethodArgumentNotValidException ex, WebRequest webRequest) {

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        final List<CustomFieldError> customFieldErrors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            final String field = fieldError.getField();
            final String message = fieldError.getDefaultMessage();
            final CustomFieldError customFieldError = CustomFieldError.builder().field(field).message(message).build();
            customFieldErrors.add(customFieldError);
        }

        return ResponseEntity.badRequest().body(customFieldErrors);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<Object> handleBindException(BindException ex, WebRequest webRequest) {

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        final List<CustomFieldError> customFieldErrors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            final String field = fieldError.getField();
            final String message = fieldError.getDefaultMessage();
            final CustomFieldError customFieldError = CustomFieldError.builder().field(field).message(message).build();
            customFieldErrors.add(customFieldError);
        }

        return ResponseEntity.badRequest().body(customFieldErrors);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {

        final Set<ConstraintViolation<?>> fieldErrors = ex.getConstraintViolations();
        final List<CustomFieldError> customFieldErrors = new ArrayList<>();

        for (ConstraintViolation<?> fieldError : fieldErrors) {
            final String field = fieldError.getMessage();
            final String message = fieldError.getMessageTemplate();
            final CustomFieldError customFieldError = CustomFieldError.builder().field(field).message(message).build();
            customFieldErrors.add(customFieldError);
        }

        return ResponseEntity.badRequest().body(customFieldErrors);
    }




}
