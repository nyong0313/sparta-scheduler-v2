package org.example.schedulerv2.user.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.user.controller.dto.DeleteUserRequestDto;
import org.example.schedulerv2.user.controller.dto.LoginRequestDto;
import org.example.schedulerv2.user.controller.dto.UserRequestDto;
import org.example.schedulerv2.user.entity.User;
import org.example.schedulerv2.user.repository.UserRepository;
import org.example.schedulerv2.user.service.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(UserRequestDto userRequestDto) {
        userRepository.save(User.of(userRequestDto));
    }

    public User login(LoginRequestDto loginRequestDto) {
        User existingUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!existingUser.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return existingUser;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return  userRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = findUserById(id);
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = findUserById(id);
        isPasswordMatch(existingUser, userRequestDto.getPassword());

        existingUser.updateUser(userRequestDto.getEmail(), userRequestDto.getEmail());
        User savedUser = userRepository.save(existingUser);
        return UserResponseDto.from(savedUser);
    }

    @Transactional
    public void deleteUserById(Long id, DeleteUserRequestDto deleteUserRequestDto) {
        User existingUser = findUserById(id);
        isPasswordMatch(existingUser, deleteUserRequestDto.getPassword());
        userRepository.delete(existingUser);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id: " + id));
    }

    @Transactional
    public void isPasswordMatch(User user, String password) {
        if(!user.getPassword().equals(password))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
}
