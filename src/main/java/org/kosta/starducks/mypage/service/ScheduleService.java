package org.kosta.starducks.mypage.service;

import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    /**
     * 특정 사원의 모든 일정 정보 가져오기
     * @return
     */
    public List<Schedule> findByEmployeeEmpId(Long empId) {
        return scheduleRepository.findByEmployeeEmpId(empId);
    }
}
