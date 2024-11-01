package com.okdk.board.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long boardSeq;

    @NotBlank(message = "Title is mandatory")
    private String boardTitle;

    @NotBlank(message = "Content is mandatory")
    private String boardContent;

    private String boardDttm;

    private String userId;
}
