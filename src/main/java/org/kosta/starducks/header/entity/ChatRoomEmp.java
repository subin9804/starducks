package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.kosta.starducks.hr.entity.Employee;

/**
 * 한 채팅방에 있는 사원 수만큼 생기는 엔티티
 * 채팅방에 연결된 1사원당 1엔티티여서
 * 이것을 제거하면 채팅방에 연결된 해당 사원도 연결 종료
 */

@Getter
@Setter
@Entity
public class ChatRoomEmp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name = "emp_id")
  private Employee employee;

  public ChatRoomEmp(ChatRoom chatRoom, Employee employee) {
    this.chatRoom = chatRoom;
    this.employee = employee;
  }

  public ChatRoomEmp() {
  }
}