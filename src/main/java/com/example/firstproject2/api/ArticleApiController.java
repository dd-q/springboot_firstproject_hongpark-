package com.example.firstproject2.api;

import com.example.firstproject2.dto.ArticleForm;
import com.example.firstproject2.entity.Article;
import com.example.firstproject2.repository.ArticleRepository;
import com.example.firstproject2.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // RestAPI 용 컨트롤러. 데이터(JSON)를 반환
//@Controller
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id) {
        Article article = articleService.show(id);
        return (article != null) ?
                ResponseEntity.status(HttpStatus.OK).body(article) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


//    // Transaction Test
//    @PostMapping("/api/transaction-test")
//    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
//        List<Article> createdList = articleService.createArticles(dtos);
//        return (createdList != null) ?
//                ResponseEntity.status(HttpStatus.OK).body(createdList):
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }

}
