package com.example.firstproject2.repository;

import com.example.firstproject2.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
                                                // 꺽쇠 안에 두 파라미터를 넣어줌. 1. 관리대상 Entity, 2. 대표값의 타입 (ex. PK)

    // 상속받고 있는 CrudRepository 클래스 'findAll()' 이라는 메소드를 재정의(Override) 함
    @Override
    ArrayList<Article> findAll();
    // 원래는 Iterable<Article> findAll(); 이었음
}
