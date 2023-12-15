package org.kosta.starducks.header.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


/**
 * 웹소켓을 통한 실시간 채팅 메시지 송수신 담당
 */
@Controller
public class LiveChatController {

  @MessageMapping("/chat/send")
  @SendTo("/topic/messages")
  public String send(String message) {
    // 메시지 처리 로직
    return message;
  }

  // 여기에 실시간 채팅 관련 메서드 추가
}
