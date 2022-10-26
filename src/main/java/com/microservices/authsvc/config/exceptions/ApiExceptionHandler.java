package com.microservices.authsvc.config.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = { ResponseStatusException.class })
    public ResponseEntity<Object> handleAPIException(ResponseStatusException e) {
        ApiExceptionPayload payload = new ApiExceptionPayload(e.getReason());
        return new ResponseEntity<Object>(payload, e.getStatus());
    }




}