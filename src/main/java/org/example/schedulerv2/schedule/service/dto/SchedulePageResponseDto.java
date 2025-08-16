package org.example.schedulerv2.schedule.service.dto;

import lombok.Getter;
import org.example.schedulerv2.comment.service.dto.CommentResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SchedulePageResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SchedulePageResponseDto(
            Long id,
            String title,
            String content,
            String username,
            int commentCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
