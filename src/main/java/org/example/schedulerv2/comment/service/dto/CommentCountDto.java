package org.example.schedulerv2.comment.service.dto;

import lombok.Getter;

@Getter
public class CommentCountDto {

    private final Long scheduleId;
    private final Long count;

    public CommentCountDto(Long scheduleId, Long count) {
        this.scheduleId = scheduleId;
        this.count = count;
    }
}