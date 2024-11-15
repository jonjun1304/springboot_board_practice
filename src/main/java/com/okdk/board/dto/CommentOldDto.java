package com.okdk.board.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.okdk.board.entity.CommentOld;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자 자동 생성
@NoArgsConstructor // 매개변수가 아예 없는 기본 생성자 자동 생성
@Getter // 각 필드 값을 조회할 수 있는 getter 메서드 자동 생성
@ToString // 모든 필드를 출력할 수 있는 toString 메서드 자동 생성
public class CommentOldDto {
    private Long id; // 댓글의 id
    @JsonProperty("article_id") //  필드명과 json 키 값이 다르면 해당 어노테이션을 활용해야함
    private Long articleId; // 댓글의 부모 id
    private String nickname; // 댓글 작성자
    private String body; // 댓글 본문

    public static CommentOldDto createCommentDto(CommentOld commentOld) {
        return new CommentOldDto(
                commentOld.getId(), // 댓글 엔티티의 id
                commentOld.getArticle().getId(), // 댓글 엔티티가 속한 부모 게시글의 id
                commentOld.getNickname(), // 댓글 엔티티의 nickname
                commentOld.getBody() // 댓글 엔티티의 body
        );
    }
}
