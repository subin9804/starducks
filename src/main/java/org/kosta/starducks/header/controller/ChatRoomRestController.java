package org.kosta.starducks.header.controller;

import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 채팅방 생성, 수정, 조회 등과 관련된 REST API
 */
@RestController
@RequestMapping("/api/chat/rooms")
public class ChatRoomRestController {

  @Autowired
  private ChatRoomService chatRoomService;

  @PostMapping("/create")
  public Long createRoom(@RequestBody ChatRoomRequestDto requestDto) {
    return chatRoomService.createChatRoom(requestDto);
  }

  @GetMapping("/{id}")
  public ChatRoomResponseDto getRoom(@PathVariable("id") Long id) {
    return chatRoomService.findById(id);
  }

  //  채팅방 목록 페이지에서 로그인한 사원과 관련된 것만 불러오기
  @GetMapping("/my-rooms")
  public List<ChatRoomResponseDto> getMyChatRooms(@AuthenticationPrincipal UserDetails userDetails) {
    Long empId = Long.parseLong(userDetails.getUsername());
    return chatRoomService.getChatRoomsForEmployee(empId);
  }
}