package org.kosta.starducks.forum.repository;

import org.kosta.starducks.forum.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
}
