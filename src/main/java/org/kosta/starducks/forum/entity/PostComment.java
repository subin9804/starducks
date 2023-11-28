package org.kosta.starducks.forum.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PostComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId; //댓글 고유 ID

  @Lob
  private String commentContent; //댓글 내용

  private LocalDateTime commentDate; //댓글 작성일

  @ManyToOne
  @JoinColumn(name = "postId")
  private ForumPost forumPost; //연결된 게시글

  @ManyToOne
  @JoinColumn(name = "empId")
  private EmpImpl empImpl; //댓글 작성자 테이블 연결

  private boolean commentDelete; //댓글 삭제 여부



}
