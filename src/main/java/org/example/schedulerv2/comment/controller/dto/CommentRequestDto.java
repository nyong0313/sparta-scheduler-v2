package org.example.schedulerv2.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 500, message = "내용은 최대 500자까지 가능합니다.")
    private String content;
}
