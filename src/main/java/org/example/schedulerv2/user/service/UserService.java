package org.example.schedulerv2.user.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.user.controller.dto.DeleteUserRequestDto;
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
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto.getUsername(), userRequestDto.getEmail(), userRequestDto.getPassword());
        User savedUser = userRepository.save(user);
        return UserResponseDto.from(savedUser);
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id: " + id));
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id: " + id));
        if(!existingUser.getPassword().equals(userRequestDto.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        existingUser.updateUser(userRequestDto.getEmail(), userRequestDto.getEmail());
        User savedUser = userRepository.save(existingUser);
        return UserResponseDto.from(savedUser);
    }

    @Transactional
    public void deleteUserById(Long id, DeleteUserRequestDto deleteUserRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id: " + id));
        if(!existingUser.getPassword().equals(deleteUserRequestDto.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        userRepository.delete(existingUser);
    }
}
