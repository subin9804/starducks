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
    return forumPostRepository.save(forumPost);
  }

  @Override
  public void deleteForumPost(Long id) {
    forumPostRepository.deleteById(id); // 삭제 전에 해당 ID의 게시글이 존재하는지 확인할 수 있습니다.
  }

  @Override
  public Optional<ForumPost> getPostById(Long id) {
    return forumPostRepository.findById(id); // 게시글 ID로 게시글 조회
  }

  @Override
  public List<ForumPost> getAllForumPosts() {
    return forumPostRepository.findAll(); // 모든 게시글 조회
  }

  @Override
  public List<ForumPost> getAllForumPostsSorted() {
    return forumPostRepository.findAllByOrderByPostDateDesc();
  }

  @Override //게시글 제목, 내용 검색 기능
  public List<ForumPost> searchPosts(String keyword) {
    return forumPostRepository.findByPostTitleContainingOrPostContentContaining(keyword, keyword);
  }

  @Override //게시글 조회수 증가 기능
  public Optional<ForumPost> getPostByIdAndUpdateView(Long id) {
    return forumPostRepository.findById(id).map(post -> {
      post.setPostView(post.getPostView() + 1); // 조회수 증가
      return forumPostRepository.save(post); //변경 사항 저장
    });
  }

}