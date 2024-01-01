package org.kosta.starducks.header.dto;

import lombok.Getter;
import org.kosta.starducks.header.entity.ChatMessage;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.assign.TypeCasting;

import java.time.format.DateTimeFormatter;

/**
 * 채팅 메시지 불러오기 등의 요청에 사용되는 DTO
 */

@Getter
public class ChatMessageResponseDto {

  private Long id;
  private Long senderId;
  private String senderName;
  private String message;
  private String createdDate;
  private String updatedDate;
  private boolean readStatus;
  private String roomName;

  public ChatMessageResponseDto(ChatMessage chatMessage) {
    this.id = chatMessage.getId();
    if (chatMessage.getSender() != null) {
      this.senderId = chatMessage.getSender().getEmpId();
      this.senderName = chatMessage.getSender().getEmpName();
    } else {
      this.senderId = null;
      this.senderName = "알 수 없는 사용자";
    }
    if (chatMessage.getChatRoom() != null) {
      this.roomName = chatMessage.getChatRoom().getRoomName();
    } else {
      this.roomName = "알 수 없는 채팅방";
    }
    this.message = chatMessage.getMessage();
    this.createdDate = chatMessage.getCreatedAt() != null ? chatMessage.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm")) : null;
    this.updatedDate = chatMessage.getModifiedAt() != null ? chatMessage.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")) : null;
    this.readStatus = chatMessage.isReadStatus();
  }
}