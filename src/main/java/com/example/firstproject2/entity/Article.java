package com.example.firstproject2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능하게 하는 어노테이션 >> 해당 클래스로 Table을 만듦.
@AllArgsConstructor  // lombok 라이브러리를 활용한 생성자 Refactoring
@NoArgsConstructor   // lombok 라이브러리의 디폴트 생성자 (:파라미터가 아무것도 없는 생성자)
@ToString
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성 어노테이션.
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public void patch(Article articleEntity) {
        if (articleEntity.title != null)
            this.title = articleEntity.title;
        if (articleEntity.content != null)
            this.content = articleEntity.content;
    }

        // Entity를 만들기위해 생성자 추가 >> Refactoring
//    public Article(Long id, String title, String content){
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }
//    @Override
//    public  String toString() {
//        return "Article{" +
//                "id =" + id +
//                ", title ='" + title + '\'' +
//                ", content ='" + content + '\'' +
//                '}';
//    }


}
