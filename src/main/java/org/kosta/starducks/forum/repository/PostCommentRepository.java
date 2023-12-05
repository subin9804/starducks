package org.kosta.starducks.forum.repository;

import org.kosta.starducks.forum.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
