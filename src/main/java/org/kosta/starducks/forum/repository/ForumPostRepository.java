package org.kosta.starducks.forum.repository;

import org.kosta.starducks.forum.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

    List<ForumPost> findByPostTitleContainingOrPostContentContaining(String title, String content);
    //제목과 내용으로 검색하는 기능

    List<ForumPost> findAllByOrderByPostDateDesc(); // 최신순 게시글 정렬
}
