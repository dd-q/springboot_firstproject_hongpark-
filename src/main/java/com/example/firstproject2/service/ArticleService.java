package com.example.firstproject2.service;

import com.example.firstproject2.dto.ArticleForm;
import com.example.firstproject2.entity.Article;
import com.example.firstproject2.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return article;
    }


    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null)
            return null;
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || id != article.getId()) {
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return null;
        }

        target.patch(article);
        return articleRepository.save(target);
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결재 실패")
        );
        return articleList;
    }
}
