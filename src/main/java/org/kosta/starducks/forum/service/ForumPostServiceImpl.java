package org.kosta.starducks.forum.service;

import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.repository.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 인터페이스로 서비스 클래스를 하나만 생성하는 경우에는 impl을 뒤에 붙여서 클래스를 만드는 게 관례
 *
 * 게시글 서비스 구현
 */

@Service
public class ForumPostServiceImpl implements ForumPostService {

  private final ForumPostRepository forumPostRepository;

  @Autowired
  public ForumPostServiceImpl(ForumPostRepository forumPostRepository) {
    this.forumPostRepository = forumPostRepository;
  }

  @Override
  public ForumPost createOrUpdateForumPost(ForumPost forumPost) {
    return null;
  }

  @Override
  public void deleteForumPost(Long id) {

  }

  @Override
  public Optional<ForumPost> getPostById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<ForumPost> getAllForumPosts() {
    return null;
  }
}
