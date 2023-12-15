package org.kosta.starducks.forum.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 작성글
 */
@Entity
public class ForumPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId; //게시글 번호 (고유 ID)

  private String postTitle; //게시글 제목

  @Lob
  private String postContent; //게시글 내용

  @CreationTimestamp
  private LocalDateTime postDate; //게시글 최초 작성 시간

  @UpdateTimestamp
  private LocalDateTime updateDate; //게시글 수정 시간

  private int postView; //조회수

  private boolean postDelete; //삭제 여부. 기본적으로 삭제 아님(false)

  @ManyToOne
  @JoinColumn(name = "empId")
  private Employee employee; //연결된 사원 아이디

  private boolean postNotice; //공지사항 여부

  @JsonManagedReference
  @OneToMany(mappedBy = "forumPost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<PostComment> comments = new ArrayList<>();

  public List<PostComment> getComments() {
    return comments;
  }

  public void setComments(List<PostComment> comments) {
    this.comments = comments;
  }



  public ForumPost() {
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public String getPostTitle() {
    return postTitle;
  }

  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }

  public String getPostContent() {
    return postContent;
  }

  public void setPostContent(String postContent) {
    this.postContent = postContent;
  }

  public LocalDateTime getPostDate() {
    return postDate;
  }

  public void setPostDate(LocalDateTime postDate) {
    this.postDate = postDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public int getPostView() {
    return postView;
  }

  public void setPostView(int postView) {
    this.postView = postView;
  }

  public boolean isPostDelete() {
    return postDelete;
  }

  public void setPostDelete(boolean postDelete) {
    this.postDelete = postDelete;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public boolean isPostNotice() {
    return postNotice;
  }

  public void setPostNotice(boolean postNotice) {
    this.postNotice = postNotice;
  }
}
