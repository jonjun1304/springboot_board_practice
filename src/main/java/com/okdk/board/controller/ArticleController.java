package com.okdk.board.controller;

import com.okdk.board.dto.ArticleForm;
import com.okdk.board.dto.CommentDto;
import com.okdk.board.entity.Article;
import com.okdk.board.repository.ArticleRepository;
import com.okdk.board.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService; // 서비스 객체 주입

    @GetMapping("/articles/new")
    public String newArticle() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info("createArticle start");
        //System.out.println(form.toString());
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article sqved = articleRepository.save(article);
        //System.out.println(sqved.toString());
        log.info(sqved.toString());
        log.info("createArticle end");
        return "redirect:/articles/"+sqved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("show start");
        log.info("id = "+id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // orElse : id값으로 데이터를 찾을 때 해당 id값이 없으면 null을 반환
        List<CommentDto> commentsDtos = commentService.comments(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos); // 댓글 목록 모델에 등록
        // 3. 뷰 페이지 반환하기
        log.info("show end");
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        log.info("index start");
        // 1. 모든 데이터 가져오기
        // 리파지토리에서 findAll 메서드를 오버라이딩해서 반환 타입을 ArrayList로 변환하면 에러 해결 why? findAll의 기본 반환 타입이 Iterable이기 때문에 에러 발생
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지 설정하기
        log.info("index end");
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        log.info("edit start");
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        log.info("edit end");
        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info("update start");
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신)
        }
        log.info("update end");
        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("delete start");
        log.info("삭제 요청이 들어왔습니다!!");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", id+"번이 삭제됐습니다!");
        }
        log.info("delete end");
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }


}
