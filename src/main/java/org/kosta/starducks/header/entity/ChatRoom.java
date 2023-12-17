package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;

import java.util.List;

/**
 * 채팅방 엔티티
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //채팅방 이름
  private String roomName;

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
  private List<ChatRoomEmp> chatRoomEmps; // 채팅방과 사원들의 관계를 나타내는 필드

  //채팅방에 속해있는 메시지 리스트
  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
  private List<ChatMessage> chatMessageList;

  @Builder
  public ChatRoom(String roomName) {
    this.roomName = roomName;
  }

//  채팅방 이름 변경 때 사용
  public Long updateRoomName(ChatRoomRequestDto requestDto) {
    this.roomName = requestDto.getRoomName();
    return this.id;
  }
}
