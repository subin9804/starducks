package org.kosta.starducks.header.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kosta.starducks.header.entity.ChatRoom;

/**
 * 채팅방과 관련된 '요청'에 사용됨
 * 채팅방 생성, 수정
 */

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {

  private String roomName;

  @Builder
  public ChatRoomRequestDto(String roomName) {
    this.roomName = roomName;
  }

  public ChatRoom toEntity() {
    return ChatRoom.builder()
        .roomName(this.roomName)
        .build();
  }
}
