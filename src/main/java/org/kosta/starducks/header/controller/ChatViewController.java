package org.kosta.starducks.header.controller;

import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.header.dto.ChatMessageResponseDto;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.service.ChatMessageService;
import org.kosta.starducks.header.service.ChatRoomService;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 채팅과 관련된 html 페이지들과 연결해주는 뷰 컨트롤러
 */
@Controller
public class ChatViewController {

  @Autowired
  private ChatRoomService chatRoomService;

  @Autowired
  private ChatMessageService chatMessageService;

  @Autowired
  private EmpService empService;

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
  public String chatEmpListPage(Model model, Principal principal) {
    Long loggedInUserId = Long.parseLong(principal.getName());
    Map<Department, List<Employee>> groupedEmployees = empService.getAllEmpExcludingLoggedInUser(loggedInUserId);
    model.addAttribute("groupedEmployees", groupedEmployees);

    return "header/chatEmpList";
  }

  // 채팅방 생성 요청 처리
  @PostMapping("/createChatRoom")
  public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomRequestDto requestDto,
                                          Principal principal) {
    Long userId = Long.parseLong(principal.getName()); // 로그인한 사용자의 ID를 가져옴
    requestDto.getEmpIds().add(userId); // 사용자 ID를 사원 ID 리스트에 추가

    Long chatRoomId = chatRoomService.createChatRoom(requestDto);

    return ResponseEntity.ok().body(chatRoomId);
  }

  // 채팅방 상세 페이지
  @GetMapping("/chatRoom/{roomId}")
  public String chatRoomPage(@PathVariable Long roomId, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
    List<ChatMessageResponseDto> messages = chatMessageService.getMessagesForChatRoom(roomId);
    String roomName = chatRoomService.getRoomName(roomId);

    model.addAttribute("messages", messages);
    model.addAttribute("currentUserId", Long.parseLong(currentUser.getUsername())); // 현재 로그인한 사용자의 ID
    model.addAttribute("roomName",roomName);

    return "header/chatRoom";
  }
}
