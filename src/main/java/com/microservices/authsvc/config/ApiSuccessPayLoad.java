package com.microservices.authsvc.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ControllerAdvice
public class ApiSuccessPayLoad {

   private String message;
    private Object body;
   private Date timestamp;
   private boolean exception;

    public ApiSuccessPayLoad(Object body) {
        this.body = body;
        this.timestamp= new Date();
        this.exception=false;

    }
    public ApiSuccessPayLoad(String message,Object body) {
        this.message=message;
        this.body = body;
        this.timestamp= new Date();
        this.exception=false;

    }
}
