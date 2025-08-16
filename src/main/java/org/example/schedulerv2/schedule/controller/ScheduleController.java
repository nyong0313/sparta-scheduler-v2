package org.example.schedulerv2.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.comment.controller.dto.CommentRequestDto;
import org.example.schedulerv2.comment.service.CommentService;
import org.example.schedulerv2.comment.service.dto.CommentResponseDto;
import org.example.schedulerv2.schedule.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.schedule.controller.dto.UpdateScheduleRequestDto;
import org.example.schedulerv2.schedule.service.ScheduleService;
import org.example.schedulerv2.common.dto.ApiResponse;
import org.example.schedulerv2.schedule.service.dto.SchedulePageResponseDto;
import org.example.schedulerv2.schedule.service.dto.ScheduleResponseDto;
import org.example.schedulerv2.user.entity.User;
import org.example.schedulerv2.user.repository.UserRepository;
import org.example.schedulerv2.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentService commentService;

    @PostMapping
    public ApiResponse<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto, HttpServletRequest request) {
        Long userId = getLoginUser(request).getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다. ID: " + userId));
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
    public ApiResponse<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto,
            HttpServletRequest request) {
        return ApiResponse.ok(scheduleService.updateSchedule(scheduleId, getLoginUser(request), updateScheduleRequestDto));
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDto> deleteSchedule(@PathVariable Long scheduleId, HttpServletRequest request) {
        scheduleService.deleteScheduleById(scheduleId, getLoginUser(request));
        return ApiResponse.of(HttpStatus.OK, "일정이 삭제되었습니다. ID: " + scheduleId);
    }

    // 댓글 생성
    @PostMapping("/{scheduleId}/comments")
    public ApiResponse<CommentResponseDto> saveComment(@PathVariable Long scheduleId, @Valid @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        User user = getLoginUser(request);
        return ApiResponse.ok(commentService.saveComment(commentRequestDto, user, scheduleId));
    }

    // 일정 페이지 조회
    @GetMapping("/pages")
    public ApiResponse<Page<SchedulePageResponseDto>> getSchedulesByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.ok(scheduleService.findAllPage(page, size));
    }

    private User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }

        Long userId = (Long) session.getAttribute("LOGIN_USER");
        return userService.findUserById(userId);
    }
}
