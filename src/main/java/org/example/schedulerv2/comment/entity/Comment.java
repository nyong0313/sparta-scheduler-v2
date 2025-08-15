package org.example.schedulerv2.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulerv2.common.entity.BaseEntity;
import org.example.schedulerv2.schedule.entity.Schedule;
import org.example.schedulerv2.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(String content, User user, Schedule schedule) {
        this.content = content;
        this.user = user;
        this.schedule = schedule;
    }

    public static Comment of(String content, User user, Schedule schedule) {
        return new Comment(content, user, schedule);
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
