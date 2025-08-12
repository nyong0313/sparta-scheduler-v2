package org.example.schedulerv2.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.service.ScheduleService;
import org.example.schedulerv2.service.dto.ApiResponse;
import org.example.schedulerv2.service.dto.ScheduleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ApiResponse<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ApiResponse.ok(scheduleService.saveSchedule(scheduleRequestDto));
    }

    @GetMapping
    public ApiResponse<List<ScheduleResponseDto>> getSchedules() {
        return ApiResponse.ok(scheduleService.getSchedules());
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDto> getScheduleById(@PathVariable Long scheduleId) {
        return ApiResponse.ok(scheduleService.getScheduleById(scheduleId));
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ApiResponse.ok(scheduleService.updateSchedule(scheduleId, scheduleRequestDto));
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDto> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteScheduleById(scheduleId);
        return ApiResponse.of(HttpStatus.OK, "일정이 삭제되었습니다. ID: " + scheduleId);
    }
}
