package com.okdk.board.service;

import com.okdk.board.dto.CommentOldDto;
import com.okdk.board.entity.Article;
import com.okdk.board.entity.CommentOld;
import com.okdk.board.repository.ArticleRepository;
import com.okdk.board.repository.CommentOldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentOldService {
    @Autowired
    private CommentOldRepository commentOldRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentOldDto> comments(Long articleId) {
        /*// 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 2. 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }*/
        // 3. 결과 반환
        return commentOldRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentOldDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentOldDto create(Long articleId, CommentOldDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));
        // 2. 댓글 엔티티 생성
        CommentOld commentOld = CommentOld.createComment(dto, article);
        // 3. 댓글 엔티티를 DB에 저장
        CommentOld created = commentOldRepository.save(commentOld);
        // 4. DTO로 변환해 반환
        return CommentOldDto.createCommentDto(created);
    }

    @Transactional
    public CommentOldDto update(Long id, CommentOldDto dto) {
        // 1. 댓글 조회 및 예외 발생
        CommentOld target = commentOldRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        // 2. 댓글 수정
        target.patch(dto);
        // 3. DB로 갱신
        CommentOld updated = commentOldRepository.save(target);
        // 4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentOldDto.createCommentDto(updated);
    }

    @Transactional
    public CommentOldDto delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        CommentOld target = commentOldRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        // 2. 댓글 삭제
        commentOldRepository.delete(target);
        // 3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentOldDto.createCommentDto(target);
    }
}

