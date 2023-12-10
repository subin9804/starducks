package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.mypage.dto.ConfBookDto;
import org.kosta.starducks.mypage.entity.ConfRoom;
import org.kosta.starducks.mypage.repository.ConfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfRoomService {

    private final ConfRepository confRepository;
    private final EmpRepository empRepository;

    /**
     * 예약된 회의 전체 조회
     * @return
     */
    public List<ConfRoom> getAll() {

        return confRepository.findAll();
    }

    /**
     * 특정 날짜에 예약된 회의 조회
     * @param day
     * @return
     */
    public List<ConfRoom> getDayList(LocalDate day) {

        return confRepository.findByRunningDay(day);
    }

    /**
     * 회의 예약
     * @return
     */
    public ConfRoom booking(ConfBookDto dto, Authentication auth) {
        // 예약자
//        CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
//        Employee emp = details.getEmployee();

        ConfRoom room = new ModelMapper().map(dto, ConfRoom.class);
        room.setBookerId(1L);
        room.setBookerNm("수빈 리");
        room.setDept("물류유통부");
        room.setStatus("booked");

        room.setRunningDay(LocalDate.parse(dto.getRunningDay()));
        room.setStartTime(LocalTime.parse(dto.getStartTime()));
        room.setEndTime(LocalTime.parse(dto.getEndTime()));

        return confRepository.save(room);
    }

    /**
     * 회의 수정 (당일 이전 회의는 수정 불가. 내가 예약한 회의만 수정 가능, 날짜랑 시간만 수정가능)
     * @param id
     * @return
     */
    public ConfRoom edit(Long id) {
        ConfRoom room = confRepository.findById(id).orElse(null);

        return room;

    }

    public void remove(Long id) {
        ConfRoom room = confRepository.findById(id).orElse(null);
        confRepository.delete(room);
    }


    /**
     * 30분마다 자동으로 오늘 예약기록을 불러와서 지난 회의의 status를 "finished"로 바꿈
     */
//    @Scheduled(fixedRate = 1800000)
//    public void statusChanged() {
//        LocalTime now = LocalTime.now();
//        LocalDate today = LocalDate.now();
//
//        List<ConfRoom> rooms = confRepository.findByRunningDay(today);
//        for (ConfRoom room : rooms) {
//            if(room.getStatus().equals("booked") && now.isAfter(room.getStartTime())) {  // 상태가 booked이며, 현재 시각이 회의 시작 시간 이후일 경우
//                room.setStatus("finished");
//
//                confRepository.save(room);
//            }
//        }
//
//    }
}
