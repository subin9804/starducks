package org.kosta.starducks.header.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kosta.starducks.header.entity.ChatRoom;

import java.util.List;

/**
 * 채팅방과 관련된 '요청'에 사용됨
 * 채팅방 생성, 수정
 */

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {

  private String roomName;
  private List<Long> empIds; //채팅방에 추가할 사원 ID 리스트

  @Builder
  public ChatRoomRequestDto(String roomName, List<Long> empIds) {
    this.roomName = roomName;
    this.empIds = empIds;
  }

  public ChatRoom toEntity() {
    return ChatRoom.builder()
        .roomName(this.roomName)
        .build();
  }
}
