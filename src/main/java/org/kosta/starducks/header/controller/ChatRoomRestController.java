package org.kosta.starducks.header.controller;

import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    return chatRoomService.save(requestDto);
  }

  @GetMapping("/{id}")
  public ChatRoomResponseDto getRoom(@PathVariable Long id) {
    return chatRoomService.findById(id);
  }

  // 여기에 필요한 다른 채팅방 관련 메서드 추가
}
