package com.okdk.board.api;

import com.okdk.board.dto.UserDto;
import com.okdk.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
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
}
