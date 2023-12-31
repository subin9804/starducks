package org.kosta.starducks.header.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.header.dto.ChatMessageRequestDto;
import org.kosta.starducks.header.dto.ChatMessageResponseDto;
import org.kosta.starducks.header.entity.ChatMessage;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.repository.ChatMessageRepository;
import org.kosta.starducks.header.repository.ChatRoomRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final EmpRepository empRepository;

  /**
   * ChatMessage 조회
   */
  @Transactional
  public ChatMessageResponseDto findById(final Long chatMessageId) {
    ChatMessage chatMessageEntity = this.chatMessageRepository.findById(chatMessageId).orElseThrow(
        () -> new IllegalArgumentException("해당 메시지가 존재하지 않습니다. chatMessageId = " + chatMessageId));

    return new ChatMessageResponseDto(chatMessageEntity);
  }

  /**
   * ChatMessage 생성
   */
  @Transactional
  public ChatMessage save(final Long chatRoomId, final ChatMessageRequestDto requestDto) {
    ChatRoom chatRoomEntity = chatRoomRepository.findById(chatRoomId)
        .orElseThrow(() -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다. chatRoomId = " + chatRoomId));

    Employee senderEntity = empRepository.findById(requestDto.getSender())
        .orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않음: " + requestDto.getSender()));

    return chatMessageRepository.save(requestDto.toEntity(senderEntity, chatRoomEntity));
  }

  /**
   * ChatMessage 삭제
   */
  @Transactional
  public void delete(final Long chatMessageId) {
    ChatMessage chatMessageEntity = this.chatMessageRepository.findById(chatMessageId).orElseThrow(
        () -> new IllegalArgumentException("해당 메시지가 존재하지 않습니다. chatMessageId = " + chatMessageId));

    this.chatMessageRepository.delete(chatMessageEntity);
  }

  /**
   * 특정 채팅방 ChatMessage 목록조회 - 작성순, List, ChatRoomId 검색
   */
  @Transactional
  public List<ChatMessageResponseDto> findAllByChatRoomIdAsc(final Long chatRoomId) {
    ChatRoom chatRoomEntity = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
        () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다. chatRoomId = " + chatRoomId));
    Sort sort = Sort.by(Sort.Direction.ASC, "id");
    List<ChatMessage> chatMessageList = this.chatMessageRepository.findAllByChatRoom(chatRoomEntity, sort);

    return chatMessageList.stream().map(ChatMessageResponseDto::new).collect(Collectors.toList());
  }


  /**
   * 채팅방에 속한 메시지를 최신 것부터 db에서 불러온다. reverse 사용해서 출력은 밑일수록 최신이 되도록 함
   *
   * @param chatRoomId
   * @return
   */
  @Transactional(readOnly = true)
  public List<ChatMessageResponseDto> getMessagesForChatRoom(final Long chatRoomId) {
    ChatRoom chatRoomEntity = chatRoomRepository.findById(chatRoomId)
        .orElseThrow(() -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다. chatRoomId = " + chatRoomId));

    Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
    List<ChatMessage> messages = chatMessageRepository.findAllByChatRoom(chatRoomEntity, sort);

    List<ChatMessageResponseDto> responseDtos = messages.stream()
        .map(ChatMessageResponseDto::new)
        .collect(Collectors.toList());

    // 메시지 목록을 역순으로 정렬. 메시지를 서버에서 최신 것부터 가져온 다음에 그걸 타임리프에서 출력할 떄는 아래일수록 최신이 된다.
    // 옛날 메시지부터 db에서 가져오면 비효율적
    // 더 최적화를 한다면 최신 10개만 불러와지고, 위로 스크롤하면 과거 10개 추가적으로 더 가져온다든지 가능
    Collections.reverse(responseDtos);

    return responseDtos;
  }
}