package com.okdk.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long commentId;         // 댓글 ID
    private Long boardSeq;           // 게시글 seq
    private String commentContent;  // 댓글 내용
    private String commentDttm;     // 작성 일자
    private String userId;          // 작성자 ID
}
