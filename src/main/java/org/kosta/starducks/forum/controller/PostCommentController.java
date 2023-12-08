package org.kosta.starducks.forum.controller;

import org.kosta.starducks.forum.dto.PostCommentDto;
import org.kosta.starducks.forum.entity.PostComment;
import org.kosta.starducks.forum.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글에 달린 댓글 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/comments")
public class PostCommentController {

  private final PostCommentService postCommentService;

  @Autowired
  public PostCommentController(PostCommentService postCommentService) {
    this.postCommentService = postCommentService;
  }

  // 댓글 생성 또는 수정을 위한 RESTful API 엔드포인트
  @PutMapping("/{commentId}")
  public ResponseEntity<?> createOrUpdateComment(@PathVariable Long commentId, @RequestBody PostCommentDto postCommentDto) {
    postCommentDto.setCommentId(commentId); // DTO에 commentId 설정
    PostComment updatedComment = postCommentService.updateCommentContent(postCommentDto);
    return ResponseEntity.ok(updatedComment); // 성공 응답
  }

  // 댓글 삭제를 위한 RESTful API 엔드포인트
  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
    postCommentService.deleteComment(commentId);
    return ResponseEntity.ok().build();
  }
}
