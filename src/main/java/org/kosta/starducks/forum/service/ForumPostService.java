package org.kosta.starducks.forum.service;

import org.kosta.starducks.forum.entity.ForumPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 서비스 인터페이스
 */
public interface ForumPostService {

  ForumPost createOrUpdateForumPost(ForumPost forumPost); // 일반 게시글 생성과 수정

  ForumPost noticeCreateOrUpdateForumPost(ForumPost forumPost); // 공지 게시글 생성과 수정 (공지 알림을 위해 일반 포스트와 분리)

  void deleteForumPost(Long id); //게시글 삭제

  Optional<ForumPost> getPostById(Long id); // 게시글 상세 정보로 가기 위한 식별 수단

  Page<ForumPost> postList(Pageable pageable); //게시글 리스트 처리

  Page<ForumPost> postSearchList(String keyword, Pageable pageable); //제목과 내용으로 검색 기능

  Optional<ForumPost> getPostByIdAndUpdateView(Long id); //조회수 기능

  List<ForumPost> getTopNotice();

  List<ForumPost> getForumList();

}
