package com.okdk.board.service;

import com.okdk.board.dto.UserDto;
import com.okdk.board.entity.User;
import com.okdk.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    // Java 8 이상에서는 DateTimeFormatter를 사용
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    // 사용자 인증
//    @Transactional
//    public String authenticate(String userId, String userPassword) {
//        return userRepository.findByUserId(userId)
//                .map(user -> user.getUserPassword().equals(userPassword) ? "SUCCESS" : "PASSWORD_INCORRECT")
//                .orElse("USER_NOT_FOUND");
//    }

    // 사용자 로그인
    @Transactional
    public ResponseEntity<String> authenticate(String userId, String userPassword) {
        String result = userRepository.findByUserId(userId)
                .map(user -> {
                    if (user.getUserPassword().equals(userPassword)) {
                        return "SUCCESS"; // 로그인 성공
                    } else {
                        return "PASSWORD_INCORRECT"; // 비밀번호 불일치
                    }
                })
                .orElse("USER_NOT_FOUND"); // 사용자 없음
        return ResponseEntity.ok(result); // 로그인 성공



    }

    // 사용자 생성
    @Transactional
    public ResponseEntity<String> createUser(UserDto userDto) {
        // 서버 측 중복 체크 id가 이미 존재한다면 return
        if (userRepository.existsById(userDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ID_ALREADY_EXISTS");
        }

        // 가입 일자 세팅
        //userDto.setJoinDttm(formatter.format(new Date()));
        userDto.setJoinDttm(LocalDateTime.now().format(formatter));

        // 최초 가입시 권한 common
        userDto.setAuthorityType("common");

        userRepository.save(toEntity(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    // ID 중복 체크
    public ResponseEntity<String> isUserIdDuplicate(String userId) {
        String result = userRepository.existsById(userId) ? "DUPLICATE" : "AVAILABLE"; // id가 존재하면 true 아니면 false
        return ResponseEntity.ok(result);
    }

    // 회원 정보 수정
    @Transactional
    public ResponseEntity<String> updateUser(String userId, UserDto userDto) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    // 기존 사용자 정보 업데이트
                    existingUser.setUserPassword(userDto.getUserPassword());
                    existingUser.setPhoneNo(userDto.getPhoneNo());
                    existingUser.setBirthday(userDto.getBirthday());
                    // 변경된 엔티티 저장
                    userRepository.save(existingUser);
                    return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER_NOT_FOUND")); // 사용자 없음
    }

    // 사용자 조회
    public ResponseEntity<UserDto> getUserById(String userId) {
        return userRepository.findByUserId(userId)
                .map(user -> ResponseEntity.ok(toDto(user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Entity -> DTO 변환
    private UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .joinDttm(user.getJoinDttm())
                .birthday(user.getBirthday())
                .phoneNo(user.getPhoneNo())
                .authorityType(user.getAuthorityType())
                .build();
    }

    // DTO -> Entity 변환
    private User toEntity(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .userPassword(userDto.getUserPassword())
                .joinDttm(userDto.getJoinDttm())
                .birthday(userDto.getBirthday())
                .phoneNo(userDto.getPhoneNo())
                .authorityType(userDto.getAuthorityType())
                .build();
    }
}