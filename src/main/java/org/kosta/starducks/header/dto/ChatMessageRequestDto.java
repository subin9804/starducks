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
  private Long chatRoomId;

  @Builder
  public ChatMessageRequestDto(String sender, String message, Long chatRoomId) {
    this.sender = sender;
    this.message = message;
    this.chatRoomId = chatRoomId;
  }

  public ChatMessage toEntity(ChatRoom chatRoom) {
    return ChatMessage.builder()
        .sender(this.sender)
        .message(this.message)
        .chatRoom(chatRoom)
        .build();
  }
}
