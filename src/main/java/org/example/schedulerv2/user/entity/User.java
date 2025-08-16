package org.example.schedulerv2.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulerv2.comment.entity.Comment;
import org.example.schedulerv2.common.entity.BaseEntity;
import org.example.schedulerv2.common.config.PasswordEncoder;
import org.example.schedulerv2.schedule.entity.Schedule;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 30)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static User of(String username, String email, String password) {
        return new User(username, email, password);
    }

    public void isPasswordMatch(String password, PasswordEncoder passwordEncoder) {
        if(!passwordEncoder.matches(password, this.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
}
