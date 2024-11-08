package com.okdk.board.service;

import com.okdk.board.dto.UserDto;
import com.okdk.board.entity.User;
import com.okdk.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 인증
    @Transactional
    public String authenticate(String userId, String userPassword) {
        return userRepository.findByUserId(userId)
                .map(user -> user.getUserPassword().equals(userPassword) ? "SUCCESS" : "PASSWORD_INCORRECT")
                .orElse("USER_NOT_FOUND");
    }

    // 사용자 생성
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .userPassword(userDto.getUserPassword())
                .joinDttm(userDto.getJoinDttm())
                .build();
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    // Entity -> DTO 변환
    private UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .joinDttm(user.getJoinDttm())
                .build();
    }
}