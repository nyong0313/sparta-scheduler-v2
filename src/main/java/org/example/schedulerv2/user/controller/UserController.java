package org.example.schedulerv2.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.user.controller.dto.UserRequestDto;
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

    @PostMapping
    public ApiResponse<UserResponseDto> saveUser(@RequestBody UserRequestDto userRequestDto) {
        return ApiResponse.ok(userService.saveUser(userRequestDto));
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
    public ApiResponse<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userDto) {
        return ApiResponse.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponseDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ApiResponse.of(HttpStatus.OK, "유저가 삭제되었습니다. ID: " + userId);
    }
}
