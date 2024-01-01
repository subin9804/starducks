package org.kosta.starducks.header.repository;

import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.entity.ChatRoomEmp;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomEmpRepository extends JpaRepository<ChatRoomEmp, Long> {

  // 특정 채팅방에 속한 사원 목록을 찾는 메서드
  List<ChatRoomEmp> findByChatRoom(ChatRoom chatRoom);

  // 특정 사원이 속한 채팅방 목록을 찾는 메서드
  List<ChatRoomEmp> findByEmployee(Employee employee);
}