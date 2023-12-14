package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.kosta.starducks.hr.entity.Employee;

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

}
