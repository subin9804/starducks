package org.kosta.starducks.forum.entity;

import jakarta.persistence.*;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;

/**
 * 게시판 게시글에 달리는 댓글 엔티티
 */
@Entity
public class PostComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId; //댓글 고유 ID

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId")
  private ForumPost forumPost; // 연결된 게시글

  @Lob
  private String commentContent; //댓글 내용

  private LocalDateTime commentDate; //댓글 작성일

  @ManyToOne
  @JoinColumn(name = "empId")
  private Employee employee; //댓글 작성자 테이블 연결 외래키

  private boolean commentDelete; //댓글 삭제 여부


  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public LocalDateTime getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(LocalDateTime commentDate) {
    this.commentDate = commentDate;
  }

  public ForumPost getForumPost() {
    return forumPost;
  }

  public void setForumPost(ForumPost forumPost) {
    this.forumPost = forumPost;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public boolean isCommentDelete() {
    return commentDelete;
  }

  public void setCommentDelete(boolean commentDelete) {
    this.commentDelete = commentDelete;
  }
}
