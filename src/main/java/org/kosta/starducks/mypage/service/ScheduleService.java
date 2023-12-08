package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 특정 사원의 모든 일정 정보 가져오기
     * @return
     */
    public List<Schedule> findByEmployeeEmpId(Long empId) {
        return scheduleRepository.findByEmployeeEmpId(empId);
    }

    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
