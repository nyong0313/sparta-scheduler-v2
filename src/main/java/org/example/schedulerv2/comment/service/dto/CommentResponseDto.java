package org.example.schedulerv2.comment.service.dto;

import lombok.Getter;
import org.example.schedulerv2.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

        private final Long id;
        private final String content;
        private final String userName;
        private final String scheduleTilte;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public CommentResponseDto(
                Long id,
                String content,
                String userName,
                String scheduleTilte,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {
                this.id = id;
                this.content = content;
                this.userName = userName;
                this.scheduleTilte = scheduleTilte;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }

        public static CommentResponseDto from(Comment comment) {
                return new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getSchedule().getTitle(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                );
        }
}
