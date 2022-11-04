package com.example.firstproject2.api;

import com.example.firstproject2.annotation.RunningTime;
import com.example.firstproject2.dto.CommentDto;
import com.example.firstproject2.entity.Comment;
import com.example.firstproject2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

        // 서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        // 서비스에게 위임
        CommentDto createdDto = commentService.create(articleId, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        CommentDto updated = commentService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 댓글 삭제
    @RunningTime
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto deleted = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

}
