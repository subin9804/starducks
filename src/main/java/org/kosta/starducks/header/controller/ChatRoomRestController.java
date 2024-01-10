package org.kosta.starducks.header.controller;

import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * 채팅방 생성, 수정 등과 관련된 REST API
 * 추후 채팅방 삭제 기능도 추가 가능
 */
@RestController
@RequestMapping("/api/chat/rooms")
public class ChatRoomRestController {

  private final ChatRoomService chatRoomService;

  public ChatRoomRestController(ChatRoomService chatRoomService) {
    this.chatRoomService = chatRoomService;
  }

  // 채팅방 생성 요청 처리
  // chatEmpList.html의 스크립트에서 채팅방 생성 부분 fetch('/api/chat/rooms/createChatRoom' 에서 사용 중
  @PostMapping("/createChatRoom")
  public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomRequestDto requestDto,
                                          Principal principal) {
    Long userId = Long.parseLong(principal.getName()); // 로그인한 사용자의 ID를 가져옴
    requestDto.getEmpIds().add(userId); // 사용자 ID를 사원 ID 리스트에 추가

    ChatRoom chatRoom = chatRoomService.createChatRoom(requestDto);
    Long chatRoomId = chatRoom.getId();

    return ResponseEntity.ok().body(chatRoomId);
  }

  //  채팅방 목록 페이지에 로그인한 사원과 관련된 것만 불러오기
  // chatRoomList.html의 스크립트 function loadChatRooms()에서 사용 중
  @GetMapping("/my-rooms")
  public List<ChatRoomResponseDto> getMyChatRooms(@AuthenticationPrincipal UserDetails userDetails) {
    Long empId = Long.parseLong(userDetails.getUsername());
    return chatRoomService.getChatRoomsForEmployee(empId);
  }
}