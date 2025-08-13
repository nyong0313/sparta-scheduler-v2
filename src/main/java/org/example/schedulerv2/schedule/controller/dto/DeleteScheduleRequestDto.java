package org.example.schedulerv2.schedule.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteScheduleRequestDto {

    @NotBlank
    private String password;
}
