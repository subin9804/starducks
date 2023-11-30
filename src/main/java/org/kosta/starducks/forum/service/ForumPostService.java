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

  List<ForumPost> getAllForumPosts(); // 게시글 목록 생성을 위해 모두 가져오기 **최신순 정렬만 필요하면 나중에 없애기**

  List<ForumPost> getAllForumPostsSorted(); //게시글 정렬해서 가져오기

  List<ForumPost> searchPosts(String keyword); //제목과 내용 검색 기능

  Optional<ForumPost> getPostByIdAndUpdateView(Long id); //조회수 기능

}
