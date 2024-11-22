package com.okdk.board.repository;

import com.okdk.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글 ID(boardSeq)에 해당하는 댓글 리스트 조회
    List<Comment> findByBoardBoardSeq(Long boardSeq);
}