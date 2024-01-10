package org.kosta.starducks.header.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.notify.NeedNotify;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.entity.ChatRoomEmp;
import org.kosta.starducks.header.repository.ChatRoomEmpRepository;
import org.kosta.starducks.header.repository.ChatRoomRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final EmpRepository empRepository;
  private final ChatRoomEmpRepository chatRoomEmpRepository;

  //  채팅방 조회
  @Transactional
  public ChatRoomResponseDto findById(final Long id) {
    ChatRoom chatRoom = this.chatRoomRepository.findById(id).orElseThrow(
        () -> new IllegalStateException("해당 채팅방이 존재하지 않습니다. id=" + id));

    return new ChatRoomResponseDto(chatRoom);
  }

  //채팅방 id를 통해서 채팅방 이름 조회
  public String getRoomName(final Long id) {
    ChatRoom chatRoom = this.chatRoomRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("해당 채팅방이 존재하지 않습니다. id=" + id));
    return chatRoom.getRoomName();
  }

  //  채팅방 생성
  @NeedNotify
  @Transactional
  public ChatRoom createChatRoom(ChatRoomRequestDto requestDto) {
    // 채팅방 엔티티 생성 및 저장
    ChatRoom chatRoom = new ChatRoom();
    chatRoom.setRoomName(requestDto.getRoomName());


    // 사원 ID 리스트를 반복하면서 각 사원과 채팅방의 관계 설정
    // 채팅방 requestDto에 있는 사원 목록이 있는 만큼 반복돼서 그만큼 ChatRoomEmp가 생성이 된다.
    List<ChatRoomEmp> chatRoomEmps = new ArrayList<ChatRoomEmp>();
    for (Long empId : requestDto.getEmpIds()) {
      Employee employee = empRepository.findById(empId)
          .orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다. ID: " + empId));
      ChatRoomEmp chatRoomEmp = new ChatRoomEmp(chatRoom, employee);
      chatRoomEmps.add(chatRoomEmp);
      chatRoomEmpRepository.save(chatRoomEmp);
    }


    chatRoom.setChatRoomEmps(chatRoomEmps);
    chatRoom = chatRoomRepository.save(chatRoom);
    return chatRoom;
  }

  //  채팅방 이름 수정
  @Transactional
  public Long update(final Long id, ChatRoomRequestDto requestDto) {
    ChatRoom chatRoom = this.chatRoomRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다. id =" + id));

    return chatRoom.updateRoomName(requestDto);
  }

  //  채팅방 삭제
  public void delete(final Long id) {
    ChatRoom chatRoom = this.chatRoomRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다. id = " + id));

    this.chatRoomRepository.delete(chatRoom);
  }

  // 로그인한 사원과 관련된 채팅방 목록 가져오기
  public List<ChatRoomResponseDto> getChatRoomsForEmployee(Long empId) {
    List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByEmployeeId(empId);
    return chatRooms.stream()
        .map(ChatRoomResponseDto::new)
        .collect(Collectors.toList());
  }
}