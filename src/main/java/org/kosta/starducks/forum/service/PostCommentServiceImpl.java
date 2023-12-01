package org.kosta.starducks.forum.service;

import org.kosta.starducks.forum.entity.PostComment;
import org.kosta.starducks.forum.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 인터페이스로 서비스 클래스를 하나만 생성하는 경우에는 impl을 뒤에 붙여서 클래스를 만드는 게 관례
 *
 * 댓글 서비스 구현
 */

@Service
public class PostCommentServiceImpl implements PostCommentService{

  private final PostCommentRepository postCommentRepository;

  @Autowired
  public PostCommentServiceImpl(PostCommentRepository postCommentRepository) {
    this.postCommentRepository = postCommentRepository;
  }

  @Override
  public PostComment createOrUpdateComment(PostComment postComment) {
    return postCommentRepository.save(postComment);
  }

  @Override
  public void deleteComment(Long id) {

  }

  @Override
  public Optional<PostComment> getCommentById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<PostComment> getAllComments() {
    return null;
  }
}
