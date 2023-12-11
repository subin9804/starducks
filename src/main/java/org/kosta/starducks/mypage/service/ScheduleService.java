package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.mypage.dto.ScheduleDTO;
import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final EmpRepository empRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;


    /**
     * 특정 사원의 모든 일정 정보 가져오기
     *
     * @return
     */
    public List<Schedule> findByEmployeeEmpId(Long empId) {
        return scheduleRepository.findByEmployeeEmpId(empId);
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule); // 저장된 Schedule 객체 반환
    }

    // 일정 상세 조회
    public ScheduleDTO findScheduleDetail(Long scheduleCode) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleCode);
        if (optionalSchedule.isPresent()) {
            Schedule scheduleDetail = optionalSchedule.get();
            return modelMapper.map(scheduleDetail, ScheduleDTO.class);
        }
        // 일정을 찾지 못한 경우에 대한 처리
        return null;
    }

    public void deleteSchedule(Long scheNo) {
        scheduleRepository.deleteById(scheNo);
    }
}