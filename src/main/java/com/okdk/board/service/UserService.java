package com.okdk.board.service;

import com.okdk.board.dto.LoginRequest;
import com.okdk.board.dto.UserDto;
import com.okdk.board.entity.User;
import com.okdk.board.repository.UserRepository;
import com.okdk.board.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    // Java 8 이상에서는 DateTimeFormatter를 사용
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private final JwtUtil jwtUtil;

    // 사용자 로그인
    @Transactional
    public ResponseEntity<HashMap<String, Object>> authenticate(LoginRequest loginRequest) {
        HashMap<String, Object> retMap = new HashMap<String, Object>();

        // 사용자 조회
        Optional<User> result = userRepository.findByUserId(loginRequest.getUserId());


        if (result.isPresent()) {
            User user = result.get();

            if (user.getUserPassword().equals(loginRequest.getUserPassword())) {
                // JWT 토큰 생성
                String token = jwtUtil.generateToken(user.getUserId());

                // 사용자 정보와 성공 메시지 추가
                retMap.put("resUser", toDto(user)); // User 엔티티를 DTO로 변환
                retMap.put("resMsg", "SUCCESS"); // 성공 메시지

                // JWT를 헤더에 추가하고 응답 반환
                return ResponseEntity.ok()
                        .header("Authorization", "Bearer " + token) // JWT를 헤더에 추가
                        .body(retMap); // 본문에 메시지와 사용자 정보 포함
            } else {
                // 비밀번호 불일치 처리
                retMap.put("resMsg", "PASSWORD_INCORRECT");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(retMap);
            }
        } else {
            // 사용자 없음 처리
            retMap.put("resMsg", "USER_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retMap);
        }
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