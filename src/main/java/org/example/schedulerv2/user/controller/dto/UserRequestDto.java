package org.example.schedulerv2.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 4, max = 10, message = "사용자명은 4자 이상 10자 이하이어야 합니다.")
    private String username;
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하이어야 합니다.")
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
