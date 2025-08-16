package org.example.schedulerv2.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.user.controller.dto.DeleteUserRequestDto;
import org.example.schedulerv2.user.controller.dto.LoginRequestDto;
import org.example.schedulerv2.user.controller.dto.UserRequestDto;
import org.example.schedulerv2.user.entity.User;
import org.example.schedulerv2.user.service.UserService;
import org.example.schedulerv2.common.dto.ApiResponse;
import org.example.schedulerv2.user.service.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.signup(userRequestDto);
        return ApiResponse.of(HttpStatus.CREATED, "회원가입 되었습니다.");
    }

    @PostMapping("/login")
    public ApiResponse<UserResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginUser = userService.login(loginRequestDto);
        HttpSession session = request.getSession(true);
        session.setAttribute("LOGIN_USER", loginUser.getId());
        return ApiResponse.of(HttpStatus.OK, "로그인 되었습니다.");
    }

    @PostMapping("/logout")
    public ApiResponse<UserResponseDto> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return ApiResponse.of(HttpStatus.OK, "로그아웃 되었습니다.");
    }

    @GetMapping
    public ApiResponse<List<UserResponseDto>> getUsers() {
        return ApiResponse.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> getUserById(@PathVariable Long userId) {
        return ApiResponse.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userRequestDto) {
        return ApiResponse.ok(userService.updateUser(userId, userRequestDto));
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponseDto> deleteUser(@PathVariable Long userId, @Valid @RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        userService.deleteUserById(userId, deleteUserRequestDto);
        return ApiResponse.of(HttpStatus.OK, "유저가 삭제되었습니다. ID: " + userId);
    }
}
