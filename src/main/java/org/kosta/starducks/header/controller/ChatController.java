package org.kosta.starducks.header.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

  @GetMapping("/chat")
  public String chatListPage() {
    return "header/chatList";
  }


  @MessageMapping("chat/send")
  @SendTo("topic/messages")
  public String send(String message) throws Exception {

    return message;
  }
}
