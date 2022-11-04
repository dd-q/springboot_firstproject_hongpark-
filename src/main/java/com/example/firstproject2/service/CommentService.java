package com.example.firstproject2.service;

import com.example.firstproject2.dto.CommentDto;
import com.example.firstproject2.entity.Article;
import com.example.firstproject2.entity.Comment;
import com.example.firstproject2.repository.ArticleRepository;
import com.example.firstproject2.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired // Article Data도 DB에서 가져와야할 필요가 있어서.
    private ArticleRepository articleRepository;


    public List<CommentDto> comments(Long articleId) {
        // 조회 : 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 변환 : 엔티티 > DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
        // 반환
//        return dtos;

        // stream 문법
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

        // AOP 적용 전
//        log.info("입력값 => {}", articleId);
//        log.info("입력값 => {}", dto);

        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId) // .orElseThrow : 만약에 없다면, 예외를 발생시키겠다는 뜻
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패. 대상 게시글이 없습니다.")); // 예외가 발생하면 아래 코드들이 실행되지 않는다.

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB로 자정
        Comment created = commentRepository.save(comment);

        // DTO로 반환
//        return CommentDto.createCommentDto(created);
        CommentDto createdDto = CommentDto.createCommentDto(created);

        // AOP 적용 전
//        log.info("반환값 => {}", createdDto);
        return createdDto;
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회
        Comment target = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("대상 댓글이 존재하지 않습니다."));

        // 댓글 수정
        target.patch(dto);

        // DB에 저장
        Comment updated = commentRepository.save(target);

        // DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);

    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("대상 댓글이 없습니다."));

        // 삭제 메서드
        commentRepository.delete(target);

        // DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}