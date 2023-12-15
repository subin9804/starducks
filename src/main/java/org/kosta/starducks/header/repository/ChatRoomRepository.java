package org.kosta.starducks.header.repository;

import org.kosta.starducks.header.entity.ChatRoom;
import org.modelmapper.internal.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  // CatRoom 조회 - RoomName 검색, 정확히 일치
  ChatRoom findByRoomName(String roomName);

  // ChatRoom 목록조회 - 기본정렬순, RoomName 검색, 정확히 일치
  List<ChatRoom> findAllByRoomName(String roomName);

  // ChatRoom 목록조회 - 조건정렬순, RoomName 검색, 정확히 일치
  List<ChatRoom> findAllByRoomName(String roomName, TypeCache.Sort sort);

  // ChatRoom 목록조회 - 기본정렬순, RoomName 검색, 포함 일치
  List<ChatRoom> findAllByRoomNameContaining(String roomName);

  // ChatRoom 목록조회 - 조건정렬순, RoomName 검색, 포함 일치
  List<ChatRoom> findAllByRoomNameContaining(String roomName, Sort sort);
}