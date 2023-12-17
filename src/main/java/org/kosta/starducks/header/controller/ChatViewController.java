package org.kosta.starducks.header.controller;

import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 채팅과 관련된 html 페이지들과 연결해주는 뷰 컨트롤러
 */
@Controller
public class ChatViewController {

  @Autowired
  private ChatRoomService chatRoomService;

//  메인 페이지. 채팅방 리스트 보여줌
@GetMapping("/chatRoomList")
public String chatListPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
  Long userId = Long.parseLong(userDetails.getUsername()); // 로그인한 사용자의 ID를 가져옴
  List<ChatRoomResponseDto> chatRooms = chatRoomService.getChatRoomsForEmployee(userId);
  model.addAttribute("chatRooms", chatRooms);
  return "header/chatRoomList";
}

  // 사원 목록 페이지
  @GetMapping("/chatEmpList")
  public String chatEmpListPage() {
    return "header/chatEmpList";
  }

  // 채팅방 생성 요청 처리
  @PostMapping("/createChatRoom")
  public String createChatRoom(@RequestParam String roomName,
                               @RequestParam List<Long> empIds,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
    Long userId = Long.parseLong(userDetails.getUsername()); // 로그인한 사용자의 ID를 가져옴
    empIds.add(userId); // 사용자 ID를 사원 ID 리스트에 추가

    ChatRoomRequestDto requestDto = new ChatRoomRequestDto(roomName, empIds);
    Long chatRoomId = chatRoomService.createChatRoom(requestDto);

    redirectAttributes.addAttribute("roomId", chatRoomId);
    return "redirect:/chatRoom/" + chatRoomId;
  }

  // 채팅방 상세 페이지
  @GetMapping("/chatRoom/{roomId}")
  public String chatRoomPage(@PathVariable Long roomId, Model model) {
    model.addAttribute("roomId", roomId);
    return "header/chatRoom"; // 채팅방 상세 페이지의 뷰 이름
  }
}
