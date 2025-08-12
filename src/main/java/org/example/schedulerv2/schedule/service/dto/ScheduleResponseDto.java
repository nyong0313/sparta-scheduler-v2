package org.example.schedulerv2.schedule.service.dto;

import lombok.Getter;
import org.example.schedulerv2.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String writerName;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScheduleResponseDto(
            Long id,
            String writerName,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt ) {
        this.id = id;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ScheduleResponseDto from(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getWriterName(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
