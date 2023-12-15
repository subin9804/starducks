package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.hr.entity.Employee;


/**
 *  채팅 메시지 엔티티
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatMessage extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //발신자
  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Employee sender;

  //메시지 내용
  private String content;

  //연결된 채팅방
  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  //상대방이 메시지 읽었는지. false면 안 읽었으니 숫자 1 표시
  private boolean readStatus = false;
}
