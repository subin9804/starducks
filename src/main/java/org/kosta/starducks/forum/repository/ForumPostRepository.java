package org.kosta.starducks.forum.repository;

import org.kosta.starducks.forum.entity.ForumPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

//    제목과 내용으로 검색 기능 구현
    Page<ForumPost> findByPostTitleContainingOrPostContentContaining(String title, String content, Pageable pageable);

    List<ForumPost> findTop2ByPostNoticeTrueOrderByPostDateDesc(); //최신 공지사항 2개 상단 고정하기
}
