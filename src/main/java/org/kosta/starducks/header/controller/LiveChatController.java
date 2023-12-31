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

  /**
   * 자바스크립트에서 JSON 형식으로 전환된 메시지가 컨트롤러로 전송이 되고,
   * requestDto를 통해서 데이터를 받아서 db에 저장이 되며, reponseDto로 전환이 돼서 리턴이 된다
   * @param roomId
   * @param messageRequest
   * @return
   */
//  클라이언트가 메시지를 보낼 때 사용되는 경로
  @MessageMapping("/chat/{roomId}/send")
//  해당 채팅방을 구독하는 클라이언트들에게 메시지를 전달. 자바스크립트를 보면 stompClient.subscribe에서 이 주소를 구독 중
  @SendTo("/topic/chatroom/{roomId}")
  public ChatMessageResponseDto send(@DestinationVariable Long roomId, ChatMessageRequestDto messageRequest) {
    ChatMessage savedMessage = chatMessageService.save(roomId, messageRequest);

    return new ChatMessageResponseDto(savedMessage);
  }
}