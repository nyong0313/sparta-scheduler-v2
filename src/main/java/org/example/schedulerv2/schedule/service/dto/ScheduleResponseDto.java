package org.example.schedulerv2.schedule.service.dto;

import lombok.Getter;
import org.example.schedulerv2.comment.service.dto.CommentResponseDto;
import org.example.schedulerv2.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<CommentResponseDto> commentResponseDtoList;

    public ScheduleResponseDto(
            Long id,
            String title,
            String contents,
            String username,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<CommentResponseDto> commentResponseDtoList) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentResponseDtoList = commentResponseDtoList;
    }

    public static ScheduleResponseDto from(Schedule schedule) {
        List<CommentResponseDto> commentResponseDtoList = schedule.getComments()
                .stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt(),
                commentResponseDtoList
        );
    }
}
