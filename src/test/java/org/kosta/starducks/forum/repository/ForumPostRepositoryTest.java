package org.kosta.starducks.forum.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kosta.starducks.forum.entity.ForumPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ForumPostRepositoryTest {

  @Autowired
  private ForumPostRepository forumPostRepository;

  @Test
  public void testSaveAndFindById() {
    // 더미 데이터 생성
    ForumPost newPost = new ForumPost();
    newPost.setPostTitle("Test Post");
    newPost.setPostContent("This is a test post content.");
    newPost.setPostDate(LocalDateTime.now());
    newPost.setPostView(0);
    newPost.setPostDelete(false);
    newPost.setPostNotice(false);

    // 더미 데이터 저장
    ForumPost savedPost = forumPostRepository.save(newPost);

    // 저장된 데이터 검색
    ForumPost foundPost = forumPostRepository.findById(savedPost.getPostId()).orElse(null);

    // 검증
    assertNotNull(foundPost);
    assertEquals("Test Post YAHOO", foundPost.getPostTitle());
    assertEquals("This is a test post content.", foundPost.getPostContent());
  }
}
