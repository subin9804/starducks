package org.kosta.starducks.forum.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 수정할 때 필요한 부분만 수정하도록 DTO 사용
 */
@Setter
@Getter
public class ForumPostUpdateDto {
  private String postTitle; // 게시글 제목
  private String postContent; // 게시글 내용
  private boolean postNotice; // 공지사항 여부


}
