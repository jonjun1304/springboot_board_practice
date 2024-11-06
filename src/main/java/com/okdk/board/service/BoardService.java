package com.okdk.board.service;

import com.okdk.board.dto.BoardDto;
import com.okdk.board.entity.Board;
import com.okdk.board.entity.User;
import com.okdk.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    // 게시글 생성
    @Transactional
    public BoardDto createBoard(BoardDto boardDto, User user) {
        Date nowDate = new Date();
        boardDto.setBoardDttm(formatter.format(nowDate));
        Board board = toEntity(boardDto, user);
        Board savedBoard = boardRepository.save(board);
        return toDto(savedBoard);
    }

    // 게시글 조회
    public Optional<BoardDto> getBoard(Long boardSeq) {
        return boardRepository.findById(boardSeq).map(this::toDto);
    }

    // 모든 게시글 조회
    public List<BoardDto> getAllBoards() {
        return boardRepository.findAllByOrderByBoardSeqDesc().stream()
                .map(this::toDto)
                .toList();
    }

    // 모든 게시글 조회 및 검색 (JPA 쿼리 방식) - 수정됨
    public List<BoardDto> searchBoards(String title, String fromDate, String toDate) { // 수정됨
        List<Board> boards = boardRepository.searchBoards2(title, fromDate, toDate); // 수정됨
        return boards.stream()
                .map(this::toDto)
                .toList();
    }

    // 게시글 수정
    @Transactional
    public Optional<BoardDto> updateBoard(Long boardSeq, BoardDto boardDto) {
        return boardRepository.findById(boardSeq).map(existingBoard -> {
            existingBoard.setBoardTitle(boardDto.getBoardTitle());
            existingBoard.setBoardContent(boardDto.getBoardContent());
            //existingBoard.setBoardDttm(boardDto.getBoardDttm());
            // 필요한 경우 user 정보를 다시 설정할 수도 있습니다.
            Board updatedBoard = boardRepository.save(existingBoard);
            return toDto(updatedBoard);
        });
    }

    // 게시글 삭제
    @Transactional
    public void deleteBoard(Long boardSeq) {
        boardRepository.deleteById(boardSeq);
    }

    // DTO -> Entity 변환
    private Board toEntity(BoardDto boardDto, User user) {
        return Board.builder()
                .boardSeq(boardDto.getBoardSeq())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .boardDttm(boardDto.getBoardDttm())
                .user(user) // User 객체를 매개변수로 받아서 설정
                .build();
    }

    // Entity -> DTO 변환
    private BoardDto toDto(Board board) {
        return BoardDto.builder()
                .boardSeq(board.getBoardSeq())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardDttm(board.getBoardDttm())
                .userId(board.getUser() != null ? board.getUser().getUserId() : null) // user_id 포함
                .build();
    }
}