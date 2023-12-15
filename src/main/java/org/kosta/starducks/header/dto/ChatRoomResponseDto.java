package org.kosta.starducks.header.dto;

import lombok.Getter;
import org.kosta.starducks.header.entity.ChatRoom;

import java.time.format.DateTimeFormatter;

@Getter
public class ChatRoomResponseDto {
  private Long id;
  private String roomName;
  private String createdDate;
  private String updatedDate;


  public ChatRoomResponseDto(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.roomName = chatRoom.getRoomName();
    this.createdDate = chatRoom.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    this.updatedDate = chatRoom.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
  }
}
