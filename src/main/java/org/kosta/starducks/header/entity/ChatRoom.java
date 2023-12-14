package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String roomName;

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
  private List<ChatMessage> chatMessageList;

  @Builder
  public ChatRoom(String roomName) {
    this.roomName = roomName;
  }

  public Long updateRoomName(ChatRoomRequestDto requestDto) {
    this.roomName = requestDto.getRoomName();
    return this.id;
  }
}
