package org.kosta.starducks.header.repository;

import org.kosta.starducks.header.entity.ChatRoom;
import org.modelmapper.internal.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
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

  // 채팅방과 관련된 사원들의 정보도 함께 로드하는 쿼리
  @Query("SELECT DISTINCT cr FROM ChatRoom cr " +
      "JOIN cr.chatRoomEmps cre " +
      "JOIN cre.employee emp " +
      "LEFT JOIN cr.chatMessageList cm " +
      "WHERE emp.empId = :empId " +
      "GROUP BY cr " +
      "ORDER BY MAX(cm.createdAt) DESC")
  List<ChatRoom> findChatRoomsByEmployeeId(@Param("empId") Long empId);
}


