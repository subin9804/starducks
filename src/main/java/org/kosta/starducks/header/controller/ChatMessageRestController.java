package org.kosta.starducks.header.controller;

import org.kosta.starducks.header.dto.ChatMessageRequestDto;
import org.kosta.starducks.header.dto.ChatMessageResponseDto;
import org.kosta.starducks.header.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 채팅 메시지 관련된 REST API 요청 처리
 * 메시지 생성, 조회 등등
 */
@RestController
@RequestMapping("/api/chat/messages")
public class ChatMessageRestController {

  @Autowired
  private ChatMessageService chatMessageService;

  @PostMapping("/send")
  public Long sendMessage(@RequestBody ChatMessageRequestDto requestDto) {
    return chatMessageService.save(requestDto.getChatRoomId(), requestDto).getId();
  }

  @GetMapping("/{id}")
  public ChatMessageResponseDto getMessage(@PathVariable("id") Long id) {
    return chatMessageService.findById(id);
  }

  // 여기에 필요한 다른 채팅 메시지 관련 메서드 추가
}