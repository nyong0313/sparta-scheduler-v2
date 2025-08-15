package org.example.schedulerv2.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulerv2.comment.entity.Comment;
import org.example.schedulerv2.common.entity.BaseEntity;
import org.example.schedulerv2.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 50)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Schedule of(String title, String content, User user) {
        return new Schedule(title, content, user);
    }

    public void updateSchedule(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
