package com.microservices.authsvc.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class TokenResDTO

{
    String userName;
    String token;
    Date expiryDate;
    String[] scope;
}
