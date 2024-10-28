package com.okdk.board.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.okdk.board.dto.ArticleForm;
import com.okdk.board.entity.Article;
import com.okdk.board.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticle() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        System.out.println(article.toString());
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article sqved = articleRepository.save(article);
        System.out.println(sqved.toString());
        return "";
    }
}
