package com.example.firstproject2.controller;

import com.example.firstproject2.dto.ArticleForm;
import com.example.firstproject2.dto.CommentDto;
import com.example.firstproject2.entity.Article;
import com.example.firstproject2.repository.ArticleRepository;
import com.example.firstproject2.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j // 로깅을 위한 어노테이션 (lombok 라이브러리)
public class ArticleController {

    // 객체 주입하기 (DI)
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO 를 Entity로 변환 >> entity 디렉토리 생성하여 Article 클래스 생성.
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository 에게 Entity를 DB 안에 저장하게 함. >> repository 디렉토리에서 ArticleRepository 인터페이스 생성.
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // Comment 가져오기
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 가져온 데이터 모델에 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 보여줄 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 article을 가져온다.
        // 타입 캐스팅 : 좌변에는 list 타입을 받기로 했는데, 우변은 iterable 타입이라 두 타입이 불일치 >> 일치시키기 (방법이 여러가지 있는데, 메서드 오버라이딩으로 해결)
        List<Article> articlelEntityList = articleRepository.findAll();  // ArrayList의 상위 타입인 List로 받음

        // 2. 가져온 article 묶음을 view로 전달
        model.addAttribute("articleList", articlelEntityList);

        // 3. 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO 를 Entity로 변환한다.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. Entity를 DB에 저장한다.
        // 2-1. DB에서 기존 데이터를 가져온다.
        Article target= articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터가 있다면, 값을 갱신한다.
        if (target != null) {
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")    // RedirectAttribute 로 휘발성(flash) 삭제 메세지 출력
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        // 1. Repository 를 통해 Entity 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 삭제 메서드 실행
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제 완료");
        }
        return "redirect:/articles";
    }
}
