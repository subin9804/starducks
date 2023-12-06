package org.kosta.starducks.forum.dto;

public class PostCommentDto {
  private Long commentId; // 댓글 ID
  private String commentContent; // 수정될 댓글 내용

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
}

