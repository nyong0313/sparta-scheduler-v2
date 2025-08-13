package org.example.schedulerv2.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
