package org.kosta.starducks.forum.dto;

/**
 * 게시글 수정할 때 필요한 부분만 수정하도록 DTO 사용
 */
public class ForumPostUpdateDTO {
  private String postTitle; // 게시글 제목
  private String postContent; // 게시글 내용
  private boolean postNotice; // 공지사항 여부

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

  public boolean isPostNotice() {
    return postNotice;
  }

  public void setPostNotice(boolean postNotice) {
    this.postNotice = postNotice;
  }



}
