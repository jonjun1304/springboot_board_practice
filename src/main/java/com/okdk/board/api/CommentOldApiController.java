package com.okdk.board.api;

import com.okdk.board.dto.CommentOldDto;
import com.okdk.board.service.CommentOldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentOldApiController {
    @Autowired
    private CommentOldService commentOldService;
    // 1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentOldDto>> comments(@PathVariable Long articleId) {
        // 서비스에 위임
        List<CommentOldDto> dtos = commentOldService.comments(articleId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentOldDto> create(@PathVariable Long articleId,
                                                @RequestBody CommentOldDto dto) {
        // 서비스에 위임
        CommentOldDto createdDto = commentOldService.create(articleId, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentOldDto> update(@PathVariable Long id,
                                                @RequestBody CommentOldDto dto) {
        // 서비스에 위임
        CommentOldDto updatedDto = commentOldService.update(id, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentOldDto> delete(@PathVariable Long id) {
        // 서비스에 위임
        CommentOldDto deletedDto = commentOldService.delete(id);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
