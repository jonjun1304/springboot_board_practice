package com.okdk.board.repository;

import com.okdk.board.entity.CommentOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentOldRepository extends JpaRepository<CommentOld, Long> {
    // 특정 게시글의 모든 댓글 조회
    /*@Query(value = "SELECT * FROM comment WHERE article_id = :articleId",
            nativeQuery = true) // value 속성에 실행하려는 쿼리 작성*/
    @Query(nativeQuery = true)
    List<CommentOld> findByArticleId(Long articleId);
    // 특정 닉네임의 모든 댓글 조회
    List<CommentOld> findByNickname(String nickname);
}
