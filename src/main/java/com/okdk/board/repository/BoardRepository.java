package com.okdk.board.repository;

import com.okdk.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 추가적인 쿼리 메서드가 필요할 경우 여기에 정의할 수 있습니다.

    // 예시: 특정 사용자 ID에 대한 게시글 찾기
    //List<Board> findByUserId(String userId);
}
