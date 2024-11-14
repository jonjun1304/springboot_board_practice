package com.okdk.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String userPassword;
    private String joinDttm;
    private String birthday;
    private String phoneNo;
    private String authorityType;
}
