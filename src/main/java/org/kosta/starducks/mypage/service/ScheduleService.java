package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

//    private final ScheduleReposiroty scheduleReposiroty;

    /**
     * 자체 매핑 메서드 생성(ModelMapper를 안쓰기 위해서)
     * @return
     */
//    public List<ScheduleDTO> findMySchedule() {
//        List<Schedule> scheduleList = scheduleReposiroty.findAll();
//        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
//
//        for(Schedule schedule : scheduleList) {
//            ScheduleDTO scheduleDTO = new ScheduleDTO(schedule);
//            scheduleDTOList.add(scheduleDTO);
//        }
//        return scheduleDTOList;
//    }

//    private ScheduleDTO scheduleDTO(Schedule schedule) {
//        ScheduleDTO scheduleDTO = new ScheduleDTO();
//        scheduleDTO.setScheNo(schedule.getScheNo());
//        scheduleDTO.setScheStartDate(schedule.getScheStartDate());
//        scheduleDTO.setScheEndDate(schedule.getScheEndDate());
//        scheduleDTO.setScheduleType(schedule.getScheduleType());
//        scheduleDTO.setLocation(schedule.getLocation());
//        scheduleDTO.setNotes(schedule.getNotes());
//        scheduleDTO.setEmpId(schedule.getEmpId());
//
//        return scheduleDTO;
//
//    }

    /**
     * Schedule 객체 저장
     * @param schedule
     */
//    public Schedule saveSchedule(Schedule schedule) {
//        return scheduleReposiroty.save(schedule);
//    }

//    public List<ScheduleDTO> getAllSchedules() {
//        List<Schedule> scheduleList = scheduleReposiroty.findAll();
//        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
//
//        for (Schedule schedule : scheduleList) {
//            ScheduleDTO scheduleDTO = new ScheduleDTO((schedule));
//            scheduleDTOList.add(scheduleDTO);
//        }
//
//        return scheduleDTOList;
//    }
}
