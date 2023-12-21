package org.kosta.starducks.header.repository;

import org.kosta.starducks.header.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

  public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  /**
   * 채팅방 목록 정렬 기준 : 해당 채팅방의 최신 메시지 기준으로 최신순 정렬
   * @return
   */
  @Query("SELECT cr FROM ChatRoom cr LEFT JOIN ChatMessage cm ON cr.id = cm.chatRoom.id "
      + "GROUP BY cr.id "
      + "ORDER BY MAX(cm.createdAt) DESC")
  List<ChatRoom> findAllOrderByLatestMessage();

  @Query("SELECT cr FROM ChatRoom cr JOIN cr.chatRoomEmps cre WHERE cre.employee.empId = :empId")
  List<ChatRoom> findChatRoomsByEmployeeId(@Param("empId") Long empId);
}