package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.hr.entity.Employee;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatMessage extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Employee sender;

  private String content;

  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  private boolean readStatus = false;
}
