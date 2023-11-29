package org.kosta.starducks.forum.service;

import org.kosta.starducks.forum.entity.ForumPost;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 서비스 인터페이스
 */
public interface ForumPostService {

  ForumPost createOrUpdateForumPost(ForumPost forumPost); //게시글 생성과 수정
  void deleteForumPost(Long id); //게시글 삭제

  Optional<ForumPost> getPostById(Long id); // 게시글 상세 정보로 가기 위한 식별 수단
  List<ForumPost> getAllForumPosts(); // 게시글 목록 생성을 위해 모두 가져오기

}
