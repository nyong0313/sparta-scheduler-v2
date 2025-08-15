package org.example.schedulerv2.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.comment.controller.dto.CommentRequestDto;
import org.example.schedulerv2.comment.entity.Comment;
import org.example.schedulerv2.comment.service.CommentService;
import org.example.schedulerv2.comment.service.dto.CommentResponseDto;
import org.example.schedulerv2.common.dto.ApiResponse;
import org.example.schedulerv2.schedule.service.ScheduleService;
import org.example.schedulerv2.user.entity.User;
import org.example.schedulerv2.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/comments")
public class CommentController {

    private CommentService commentService;
    private ScheduleService scheduleService;
    private UserService userService;

    @GetMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> getCommentById(@PathVariable Long commentId) {
        return ApiResponse.ok(commentService.getCommentById(commentId));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return ApiResponse.of(HttpStatus.OK, "댓글이 삭제되었습니다. ID: " + commentId);
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
