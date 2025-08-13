package org.example.schedulerv2.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulerv2.common.entity.BaseEntity;
import org.example.schedulerv2.schedule.entity.Schedule;
import org.example.schedulerv2.user.controller.dto.UserRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static User of(UserRequestDto userRequestDto) {
        return new User(userRequestDto.getUsername(), userRequestDto.getEmail(), userRequestDto.getPassword());
    }
}
