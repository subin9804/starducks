package org.kosta.starducks.forum.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.kosta.starducks.commons.notify.dto.NotifyMessage;
import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.commons.notify.service.NotifyInfo;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 작성글
 */
@Setter
@Getter
@Entity @Builder
@AllArgsConstructor
public class ForumPost implements NotifyInfo {

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


  public ForumPost() {
  }

  @Override
  public List<Employee> getReceivers() {
    return null;
  }

  @Override
  public String getGoUrl() {
    return "/forum/post/" + postId;
  }

  @Override
  public String getMsg() {
    return NotifyMessage.POST_NEW_REQUEST.getMessage();
  }

  @Override
  public Notify.NotificationType getNotificationType() {
    return Notify.NotificationType.POST;
  }
}
