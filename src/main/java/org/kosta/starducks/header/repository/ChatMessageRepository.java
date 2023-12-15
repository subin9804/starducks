package org.kosta.starducks.header.repository;

import org.kosta.starducks.header.entity.ChatMessage;
import org.kosta.starducks.header.entity.ChatRoom;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

  // ChatMessage 목록조회 - 기본정렬순, ChatRoom 검색
  List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom);

  // ChatMessage 목록조회 - 조건정렬순, ChatRoom 검색
  List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom, Sort sort);

  // ChatMessage 검색조회 - 기본정렬순, Message 검색
  List<ChatMessage> findAllByMessageContaining(String message);

  // ChatMessage 검색조회 - 조건정렬순, Message 검색
  List<ChatMessage> findAllByMessageContaining(String message, Sort sort);
}
