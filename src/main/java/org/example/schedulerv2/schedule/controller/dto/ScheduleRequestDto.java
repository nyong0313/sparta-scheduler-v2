package org.example.schedulerv2.schedule.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 20, message = "제목은 최대 20자까지 가능합니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 500, message = "내용은 최대 500자까지 가능합니다.")
    private String contents;
}
