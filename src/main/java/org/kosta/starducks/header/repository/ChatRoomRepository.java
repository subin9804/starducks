package org.kosta.starducks.header.repository;

import org.kosta.starducks.header.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  /**
   * 채팅방 목록 정렬 기준 : 해당 채팅방의 최신 메시지 기준으로 최신순 정렬
   *
   * @return
   */
  @Query("SELECT cr FROM ChatRoom cr LEFT JOIN ChatMessage cm ON cr.id = cm.chatRoom.id "
      + "GROUP BY cr.id "
      + "ORDER BY MAX(cm.createdAt) DESC")
  List<ChatRoom> findAllOrderByLatestMessage();

  /**
   * 로그인한 사원과 관련된 채팅방 목록만 가져오기 위해서 사용된다.
   * 메시지가 더 최근에 온 채팅방일수록 위쪽에 보이도록 정렬한다.
   * findAllOrderByLatestMessage 대신에 이 메서드를 사용. 채팅방 목록에서 사원 이름도 표시하기 위해서.
   * 채팅방을 방금 생성해서 메시지가 없더라도 채팅방 목록에 표시되게 하기 위해서 LEFT JOIN 사용
   *
   * @param empId
   * @return
   */
  @Query("SELECT DISTINCT cr FROM ChatRoom cr " +
      "JOIN cr.chatRoomEmps cre " +
      "JOIN cre.employee emp " +
      "LEFT JOIN cr.chatMessageList cm " +
      "WHERE emp.empId = :empId " +
      "GROUP BY cr " +
      "ORDER BY MAX(cm.createdAt) DESC")
  List<ChatRoom> findChatRoomsByEmployeeId(@Param("empId") Long empId);
}