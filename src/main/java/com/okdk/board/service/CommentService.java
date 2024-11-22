package com.okdk.board.service;

import com.okdk.board.dto.CommentDto;
import com.okdk.board.entity.Board;
import com.okdk.board.entity.Comment;
import com.okdk.board.entity.User;
import com.okdk.board.repository.BoardRepository;
import com.okdk.board.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    // 댓글 생성
    @Transactional
    public ResponseEntity<String> addComment(CommentDto commentDto) {
        return boardRepository.findById(commentDto.getBoardSeq())
                .map(board -> {
                    commentDto.setCommentDttm(LocalDateTime.now().format(formatter));
                    Comment comment = toEntity(commentDto);
                    commentRepository.save(comment);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("BOARD_NOT_FOUND"));
    }

    // 특정 게시글의 댓글 조회
    public ResponseEntity<List<CommentDto>> getCommentsByBoard(Long boardSeq) {
        if (!boardRepository.existsById(boardSeq)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<CommentDto> comments = commentRepository.findByBoardBoardSeq(boardSeq)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<String> updateComment(CommentDto commentDto) {
        return commentRepository.findById(commentDto.getCommentId())
                .map(existingComment -> {
                    existingComment.setCommentContent(commentDto.getCommentContent());
                    commentRepository.save(existingComment);
                    return ResponseEntity.status(HttpStatus.OK).body("Comment updated successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("COMMENT_NOT_FOUND"));
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("COMMENT_NOT_FOUND");
        }

        commentRepository.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Comment deleted successfully");
    }

    // DTO → 엔터티 변환
    private Comment toEntity(CommentDto commentDto) {
        Board board = new Board();
        board.setBoardSeq(commentDto.getBoardSeq());
        User user = new User();
        user.setUserId(commentDto.getUserId());

        return Comment.builder()
                .board(board)
                .commentContent(commentDto.getCommentContent())
                .commentDttm(commentDto.getCommentDttm())
                .user(user)
                .build();
    }

    // 엔터티 → DTO 변환
    private CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getCommentId())
                .boardSeq(comment.getBoard().getBoardSeq() != null ? comment.getBoard().getBoardSeq() : null)
                .commentContent(comment.getCommentContent())
                .commentDttm(comment.getCommentDttm())
                .userId(comment.getUser().getUserId() != null ? comment.getUser().getUserId() : null)
                .build();
    }
}
