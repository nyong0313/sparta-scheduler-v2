package org.example.schedulerv2.schedule.service.dto;

import lombok.Getter;
import org.example.schedulerv2.schedule.entity.Schedule;
import org.example.schedulerv2.user.entity.User;
import org.example.schedulerv2.user.service.dto.UserResponseDto;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScheduleResponseDto(
            Long id,
            String title,
            String contents,
            String username,
            LocalDateTime createdAt,
            LocalDateTime updatedAt ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ScheduleResponseDto from(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
