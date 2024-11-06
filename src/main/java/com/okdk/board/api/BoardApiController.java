package com.okdk.board.api;

import com.okdk.board.dto.BoardDto;
import com.okdk.board.entity.User;
import com.okdk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/new")
    public ResponseEntity<BoardDto> createBoard(@Valid @RequestBody BoardDto boardDto) {
        User user = new User();
        user.setUserId(boardDto.getUserId()); // 실제로는 user 정보를 DB에서 조회하여 가져와야 합니다.
        BoardDto createdBoard = boardService.createBoard(boardDto, user);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    // 게시글 조회
    @GetMapping("/{boardSeq}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable Long boardSeq) {
        return boardService.getBoard(boardSeq)
                .map(boardDto -> new ResponseEntity<>(boardDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 모든 게시글 조회 및 검색 기능 추가
    @GetMapping // 수정됨
    public ResponseEntity<List<BoardDto>> getAllBoards(
            @RequestParam(required = false) String title,  // 수정됨
            @RequestParam String fromDate,  // 수정됨
            @RequestParam String toDate) {  // 수정됨

        List<BoardDto> boards = boardService.searchBoards(title, fromDate, toDate); // 수정됨
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 모든 게시글 조회
//    @GetMapping
//    public ResponseEntity<List<BoardDto>> getAllBoards() {
//        List<BoardDto> boards = boardService.getAllBoards();
//        return new ResponseEntity<>(boards, HttpStatus.OK);
//    }

    // 게시글 수정
    @PutMapping("/{boardSeq}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable Long boardSeq, @Valid @RequestBody BoardDto boardDto) {
        return boardService.updateBoard(boardSeq, boardDto)
                .map(updatedBoard -> new ResponseEntity<>(updatedBoard, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardSeq}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardSeq) {
        boardService.deleteBoard(boardSeq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
