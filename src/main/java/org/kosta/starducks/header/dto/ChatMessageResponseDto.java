package org.kosta.starducks.header.dto;

import lombok.Getter;
import org.kosta.starducks.header.entity.ChatMessage;

import java.time.format.DateTimeFormatter;

/**
 * 채팅 메시지 불러오기 등의 요청에 사용되는 DTO
 */

@Getter
public class ChatMessageResponseDto {

  private Long id;
  private String sender;
  private String message;
  private String createdDate;
  private String updatedDate;

  public ChatMessageResponseDto(ChatMessage chatMessage) {
    this.id = chatMessage.getId();
    this.sender = chatMessage.getSender();
    this.message = chatMessage.getMessage();
    this.createdDate = chatMessage.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    this.updatedDate = chatMessage.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
  }
}
