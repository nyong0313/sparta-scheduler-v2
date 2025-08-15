package org.example.schedulerv2.user.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.config.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserRequestDto userRequestDto) {
        if(userRepository.findByEmail(userRequestDto.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(User.of(userRequestDto.getUsername(), userRequestDto.getEmail(), userRequestDto.getPassword()));
    }

    @Transactional
    public User login(LoginRequestDto loginRequestDto) {
        User existingUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        existingUser.isPasswordMatch(loginRequestDto.getPassword(), passwordEncoder);

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
        existingUser.isPasswordMatch(userRequestDto.getPassword(), passwordEncoder);

        existingUser.updateUser(userRequestDto.getEmail(), userRequestDto.getEmail());
        User savedUser = userRepository.save(existingUser);
        return UserResponseDto.from(savedUser);
    }

    @Transactional
    public void deleteUserById(Long id, DeleteUserRequestDto deleteUserRequestDto) {
        User existingUser = findUserById(id);
        existingUser.isPasswordMatch(deleteUserRequestDto.getPassword(), passwordEncoder);
        userRepository.delete(existingUser);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id: " + id));
    }
}
