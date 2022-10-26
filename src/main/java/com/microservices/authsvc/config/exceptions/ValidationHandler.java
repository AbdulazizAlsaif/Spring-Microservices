package com.microservices.authsvc.config.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errors=ex.getBindingResult().getAllErrors();
        Map<String, Object> errorsMap = new HashMap<>();
        errors.forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorsMap.put(fieldName, message);
        });
        ApiExceptionPayload payload = new ApiExceptionPayload("Validation Error",errorsMap);
        return new ResponseEntity<Object>(payload, HttpStatus.BAD_REQUEST);
    }

}