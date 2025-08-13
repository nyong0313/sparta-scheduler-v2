package org.example.schedulerv2.schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulerv2.common.entity.BaseEntity;
import org.example.schedulerv2.schedule.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 50)
    private String contents;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public static Schedule of(ScheduleRequestDto scheduleRequestDto, User user) {
        return new Schedule(scheduleRequestDto.getTitle(), scheduleRequestDto.getContents(), user);
    }

    public void updateSchedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
