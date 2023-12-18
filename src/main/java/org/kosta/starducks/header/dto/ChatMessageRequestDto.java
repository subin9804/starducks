package org.kosta.starducks.header.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.header.entity.ChatMessage;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.hr.entity.Employee;

/**
 * 채팅 메시지 생성 요청 등을 위한 DTO
 */

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {

  private Long sender;
  private String message;
  private Long chatRoomId;

  @Builder
  public ChatMessageRequestDto(Long sender, String message, Long chatRoomId) {
    this.sender = sender;
    this.message = message;
    this.chatRoomId = chatRoomId;
  }

  public ChatMessage toEntity(Employee senderEntity, ChatRoom chatRoom) {
    return ChatMessage.builder()
        .sender(senderEntity)
        .message(this.message)
        .chatRoom(chatRoom)
        .build();
  }
}