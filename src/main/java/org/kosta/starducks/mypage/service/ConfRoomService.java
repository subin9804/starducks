package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.mypage.entity.ConfRoom;
import org.kosta.starducks.mypage.repository.ConfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfRoomService {

    private final ConfRepository confRepository;

    /**
     * 예약된 회의 전체 조회 (쓸일없음)
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
//    public List<ConfRoom> getDayList(LocalDate day) {
//
////        return confRepository.getAllByRunningDay();
//    }

    /**
     * 회의 예약
     * @return
     */
    public ConfRoom booking(ConfRoom room) {

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

}
