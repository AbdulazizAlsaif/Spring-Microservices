package com.microservices.authsvc.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
public class UserReqDTO {

    @NotNull(message = "Name is mandatory")
    @Size(min = 3, message = "username should have at least 3 characters")
    String username;
    @NotNull(message = "Password is mandatory")
    @Size(min = 3, message = "Password should have at least 3 characters")
    String password;
}
