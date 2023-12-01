package org.kosta.starducks.forum.service;

import org.kosta.starducks.forum.entity.PostComment;

import java.util.List;
import java.util.Optional;

/**
 * 게시글에 달리는 댓글 관련 서비스 인터페이스
 */
public interface PostCommentService {

  PostComment createOrUpdateComment(PostComment postComment); //댓글 생성과 수정
  void deleteComment(Long id); //댓글 삭제

  Optional<PostComment> getCommentById(Long id);
  List<PostComment> getAllComments(); //게시글에 달린 댓글 목록 생성



}
