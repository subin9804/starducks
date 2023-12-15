package org.kosta.starducks.header.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.header.entity.ChatMessage;
import org.kosta.starducks.header.entity.ChatRoom;

/**
 * 채팅 메시지 생성 요청 등을 위한 DTO
 */

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {

  private String sender;
  private String message;
  private ChatRoom chatRoom;

  @Builder
  public ChatMessageRequestDto(String sender, String message, ChatRoom chatRoom) {
    this.sender = sender;
    this.message = message;
    this.chatRoom = chatRoom;
  }

  public ChatMessage toEntity() {
    return ChatMessage.builder()
        .sender(this.sender)
        .message(this.message)
        .chatRoom(this.chatRoom)
        .build();
  }
}
