package org.example.schedulerv2.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.schedule.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.schedule.service.ScheduleService;
import org.example.schedulerv2.common.dto.ApiResponse;
import org.example.schedulerv2.schedule.service.dto.ScheduleResponseDto;
import org.example.schedulerv2.user.entity.User;
import org.example.schedulerv2.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserRepository userRepository;

    @PostMapping
    public ApiResponse<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        Long tempUserId = 1L; // 임시 유저 ID
        User user = userRepository.findById(tempUserId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다. ID: " + tempUserId));
        return ApiResponse.ok(scheduleService.saveSchedule(scheduleRequestDto, user));
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
