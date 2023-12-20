package org.kosta.starducks.header.dto;

import lombok.Getter;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.entity.ChatRoomEmp;
import org.kosta.starducks.hr.entity.Employee;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChatRoomResponseDto {
  private Long id;
  private String roomName;
  private String createdDate;
  private String updatedDate;
  private List<String> empNames; // 채팅방에 속한 사원들의 이름 목록


  public ChatRoomResponseDto(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.roomName = chatRoom.getRoomName();
    this.createdDate = chatRoom.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    this.updatedDate = (chatRoom.getModifiedAt() != null) ?
        chatRoom.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")) :
        "N/A"; // 'N/A' 또는 다른 기본값을 사용
    // 채팅방에 속한 사원들의 이름 목록을 추가
    this.empNames = chatRoom.getChatRoomEmps().stream()
        .map(ChatRoomEmp::getEmployee)
        .map(Employee::getEmpName)
        .collect(Collectors.toList());
  }
}
