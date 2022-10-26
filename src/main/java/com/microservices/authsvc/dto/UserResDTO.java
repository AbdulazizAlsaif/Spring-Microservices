package com.microservices.authsvc.dto;

import com.microservices.authsvc.models.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter @Getter
public class UserResDTO {
    private  Long id;
    private String username;


    public static UserResDTO UserToUserResDTO(User user){

    return new UserResDTO(user.getId(),user.getName());
    }
}
