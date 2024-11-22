package com.okdk.board.controller;

import com.okdk.board.dto.CommentDto;
import com.okdk.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    // 특정 게시글의 댓글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentDto>> getCommentsByBoard(@PathVariable Long boardId) {
        return commentService.getCommentsByBoard(boardId);
    }

    // 댓글 수정
    @PutMapping
    public ResponseEntity<String> updateComment(@RequestBody CommentDto commentDto) {
        return commentService.updateComment(commentDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }









}
