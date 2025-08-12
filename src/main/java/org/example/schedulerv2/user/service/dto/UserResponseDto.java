package org.example.schedulerv2.user.service.dto;

import lombok.Getter;
import org.example.schedulerv2.user.entity.User;

@Getter
public class UserResponseDto {

    private String username;
    private String email;

    public UserResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getUsername(), user.getEmail());
    }
}
