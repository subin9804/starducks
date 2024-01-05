package org.kosta.starducks.forum.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;

/**
 * 게시판 게시글에 달리는 댓글 엔티티
 */
@Setter
@Getter
@Entity
public class PostComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId; //댓글 고유 ID

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId")
  private ForumPost forumPost;

  @Lob
  private String commentContent; //댓글 내용

  @CreationTimestamp
  private LocalDateTime commentDate; //댓글 작성일

  @ManyToOne
  @JoinColumn(name = "empId")
  private Employee employee; //댓글 작성자 테이블 연결 외래키

  private boolean commentDelete; //댓글 삭제 여부


}
