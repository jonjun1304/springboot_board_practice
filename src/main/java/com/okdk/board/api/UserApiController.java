package com.okdk.board.api;

import com.okdk.board.dto.UserDto;
import com.okdk.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody UserDto userDto) {
        return userService.authenticate(userDto.getUserId(), userDto.getUserPassword());
    }

    // ID 중복 체크
    @PostMapping("/check-id")
    public ResponseEntity<String> isUserIdDuplicate(@RequestBody UserDto userDto) {
        return userService.isUserIdDuplicate(userDto.getUserId());
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    // 회원 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    // 회원수정
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable String userId,
            @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }
}
