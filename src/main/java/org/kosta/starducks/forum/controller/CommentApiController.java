package org.kosta.starducks.forum.controller;

import org.kosta.starducks.forum.dto.PostCommentDto;
import org.kosta.starducks.forum.service.PostCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
  private final PostCommentService postCommentService;

  public CommentApiController(PostCommentService postCommentService) {
    this.postCommentService = postCommentService;
  }


  @PutMapping("/{id}")
  public ResponseEntity<String> createOrUpdateComment(@PathVariable Long id, @RequestBody PostCommentDto postCommentDto) {
    // 댓글 수정 로직
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteComment(@PathVariable Long id) {
    // 댓글 삭제 로직
    return ResponseEntity.ok().build();
  }
}
