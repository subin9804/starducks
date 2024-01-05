package org.kosta.starducks.forum.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCommentDto {
  private Long commentId; // 댓글 ID
  private String commentContent; // 수정될 댓글 내용
  private Long postId;

}

