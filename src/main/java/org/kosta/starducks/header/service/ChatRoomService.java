package org.kosta.starducks.header.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.header.dto.ChatRoomRequestDto;
import org.kosta.starducks.header.dto.ChatRoomResponseDto;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.entity.ChatRoomEmp;
import org.kosta.starducks.header.repository.ChatRoomEmpRepository;
import org.kosta.starducks.header.repository.ChatRoomRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final EmpRepository empRepository;

  @Autowired
  private final ChatRoomEmpRepository chatRoomEmpRepository;


  //  채팅방 조회
  @Transactional
  public ChatRoomResponseDto findById(final Long id) {
    ChatRoom chatRoom = this.chatRoomRepository.findById(id).orElseThrow(
        () -> new IllegalStateException("해당 채팅방이 존재하지 않습니다. id=" + id));

    return new ChatRoomResponseDto(chatRoom);
  }

  //채팅방 id를 통해서 이름 조회
  public String getRoomName(final Long id) {
    ChatRoom chatRoom = this.chatRoomRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("해당 채팅방이 존재하지 않습니다. id=" + id));
    return chatRoom.getRoomName();
  }

  //  채팅방 생성
  @Transactional
  public Long createChatRoom(ChatRoomRequestDto requestDto) {
    // 채팅방 엔티티 생성 및 저장
    ChatRoom chatRoom = new ChatRoom();
    chatRoom.setRoomName(requestDto.getRoomName());
    chatRoom = chatRoomRepository.save(chatRoom);

    // 사원 ID 리스트를 반복하면서 각 사원과 채팅방의 관계 설정
    for (Long empId : requestDto.getEmpIds()) {
      Employee employee = empRepository.findById(empId)
          .orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다. ID: " + empId));
      ChatRoomEmp chatRoomEmp = new ChatRoomEmp(chatRoom, employee);
      chatRoomEmpRepository.save(chatRoomEmp);
    }

    return chatRoom.getId();
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

  // ChatRoom 목록조회 - 최신순, List
  @Transactional
  public List<ChatRoomResponseDto> findALlDesc() {
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    List<ChatRoom> chatRoomList = this.chatRoomRepository.findAll(sort);

    return chatRoomList.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
  }

   // ChatRoom 검색목록조회 - 최신순, List
  @Transactional
  public List<ChatRoomResponseDto> findAllByRoomNameDesc(String roomName) {
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    List<ChatRoom> chatRoomList = this.chatRoomRepository.findAllByRoomNameContaining(roomName, sort);
    return chatRoomList.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
  }

  public List<ChatRoomResponseDto> getChatRoomsForEmployee(Long empId) {
    List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByEmployeeId(empId);
    return chatRooms.stream()
        .map(ChatRoomResponseDto::new)
        .collect(Collectors.toList());
  }

}
