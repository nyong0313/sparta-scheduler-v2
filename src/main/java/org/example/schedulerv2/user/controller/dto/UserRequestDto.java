package org.example.schedulerv2.user.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String username;
    @Email
    private String email;
    private String password;
}
