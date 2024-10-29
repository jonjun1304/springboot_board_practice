package com.okdk.board.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import org.springframework.stereotype.Controller;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column//(name = "id")
    private Long id;
    @Column//(name = "title")
    private String title;
    @Column//(name = "content")
    private String content;

    public void patch(Article article) {
        if (article.title != null) {
            this.title = article.getTitle();
        }
        if (article.content != null) {
            this.content = article.getContent();
        }
    }
}
