package org.kosta.starducks.header.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.commons.notify.dto.NotifyMessage;
import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.commons.notify.service.NotifyInfo;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.hr.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 채팅방 엔티티
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity implements NotifyInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //채팅방 이름
  private String roomName;

  //채팅방과 연관된 사원들을 연결해 주는 채팅방-사원 리스트
  //채팅방이 이 관계의 주인. 채팅방 엔티티에 변경이 있으면 chatRoomEmp에도 적용된다.
  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
  private List<ChatRoomEmp> chatRoomEmps;

  //채팅방에 속해있는 메시지 리스트
  //채팅방이 이 관계의 주인. 채팅방 엔티티가 삭제되면 속한 메시지들도 삭제된다.
  //메시지들을 db에 유지하려면 cascade 없애기
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

  @Override
  public List<Employee> getReceivers() {
    List<Employee> emps = chatRoomEmps.stream()
            .map(ChatRoomEmp::getEmployee)
            .collect(Collectors.toList());
    System.out.println(emps);

    return emps;
  }

  @Override
  public String getGoUrl() {
    return "/chatRoom/" + id;
  }

  @Override
  public String getMsg() {
    return NotifyMessage.CHAT_NEW_REQUEST.getMessage();
  }

  @Override
  public Notify.NotificationType getNotificationType() {
    return Notify.NotificationType.CHAT;
  }
}