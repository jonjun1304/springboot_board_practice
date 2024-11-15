package com.okdk.board.repository;

import com.okdk.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 추가적인 쿼리 메서드가 필요할 경우 여기에 정의할 수 있습니다.

    // 예시: 특정 사용자 ID에 대한 게시글 찾기
    //List<Board> findByUserId(String userId);

    // 전체 게시글 조회 seq 내림차순
//    @Query("SELECT b FROM Board b ORDER BY b.boardSeq DESC")
//    List<Board> findAllBoardsByDesc();
    List<Board> findAllByOrderByBoardSeqDesc();

    // 제목과 작성일자 범위로 검색 (JPA 쿼리 사용) - 수정됨
    @Query("SELECT b FROM Board b WHERE b.boardDttm LIKE CONCAT(:fromDate, '%') AND b.boardDttm LIKE CONCAT(:toDate, '%') " +
            "AND (:title IS NULL OR :title = '' OR b.boardTitle LIKE %:title%) ORDER BY b.boardSeq DESC")
    List<Board> searchBoards(@Param("title") String title, @Param("fromDate") String fromDate, @Param("toDate") String toDate); // 수정됨

    @Query(name = "Board.searchBoards2", nativeQuery = true)
    List<Board> searchBoards2(@Param("title") String title, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
