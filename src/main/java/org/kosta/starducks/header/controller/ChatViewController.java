package org.kosta.starducks.header.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 채팅과 관련된 html 페이지들과 연결해주는 뷰 컨트롤러
 */
@Controller
public class ChatViewController {

//  메인 페이지. 채팅방 리스트 보여줌
  @GetMapping("/chat")
  public String chatListPage() {
    return "chatRoomList";
  }
}
