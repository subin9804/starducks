package org.kosta.starducks.header.controller;

import org.kosta.starducks.header.dto.ChatMessageRequestDto;
import org.kosta.starducks.header.dto.ChatMessageResponseDto;
import org.kosta.starducks.header.entity.ChatMessage;
import org.kosta.starducks.header.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


/**
 * 웹소켓을 통한 실시간 채팅 메시지 송수신 담당
 */
@Controller
public class LiveChatController {

  private final ChatMessageService chatMessageService;

  @Autowired
  public LiveChatController(ChatMessageService chatMessageService) {
    this.chatMessageService = chatMessageService;
  }

  @MessageMapping("/chat/{roomId}/send")
  @SendTo("/topic/chatroom/{roomId}")
  public ChatMessageResponseDto send(@DestinationVariable Long roomId, ChatMessageRequestDto messageRequest) {
    ChatMessage savedMessage = chatMessageService.save(roomId, messageRequest);

    return new ChatMessageResponseDto(savedMessage);
  }
}
