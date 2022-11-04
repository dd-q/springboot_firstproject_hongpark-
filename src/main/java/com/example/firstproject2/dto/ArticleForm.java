package com.example.firstproject2.dto;

import com.example.firstproject2.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

// Refactoring
@AllArgsConstructor
@ToString
public class ArticleForm {


    private Long id;
    private String title;
    private String content;

    // Refactoring
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    // Refactoring
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
