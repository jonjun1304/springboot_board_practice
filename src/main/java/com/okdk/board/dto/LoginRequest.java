package com.okdk.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userId;       // 사용자 ID
    private String userPassword; // 사용자 비밀번호
}
