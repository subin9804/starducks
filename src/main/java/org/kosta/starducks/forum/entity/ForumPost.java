package org.kosta.starducks.forum.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ForumPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId; //게시글 번호 (고유 ID)

  private String postTitle; //게시글 제목

  @Lob
  private String postContent; //게시글 내용

  private LocalDateTime postDate; //게시글 최초 작성일
  private int postView; //조회수. 로직 추가해서 동일한 사용자면 조회수 카운트 방지 가능
  private boolean postDelete; //삭제 여부. 기본적으로 삭제 아님(false)

  @ManyToOne
  @JoinColumn(name = "empId")
  private EmpImpl empImpl;

  private boolean postNotice; //공지로 쓸거니?

}
