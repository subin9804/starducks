package org.kosta.starducks.mypage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.mypage.dto.ScheduleDTO;
import org.kosta.starducks.mypage.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public String formTest(Model model) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        model.addAttribute("scheduleDTO", scheduleDTO);
        return "mypage/schedule/registModal2";
    }

    @PostMapping("/schedule/success")
    public String successMethod(ScheduleDTO scheduleDTO) {
        System.out.println("scheduleDTO.toString() ==> " + scheduleDTO.toString()); /** DTO에 폼 데이터가 잘 담겼는지 확인*/
        return "redirect:/mypage/schedule/success";
    }



//    @GetMapping("/schedule")
//    public String showSchedule(Model model, @ModelAttribute ScheduleDTO scheduleDTO) {
//        List<ScheduleDTO> schedules = scheduleService.getAllSchedules();  // 일정 목록 조회
//        model.addAttribute("schedules", schedules); // 일정 목록을 모델에 추가
//        return "mypage/schedule/schedule";  // 일정 조회 페이지로 이동
//    }
//
//    @PostMapping("/schedule")
//    public String addSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {
//        Schedule schedule = new Schedule();
//
//        schedule.setScheTitle(scheduleDTO.getScheTitle());
//        schedule.setScheStartDate(scheduleDTO.getScheStartDate());
//        schedule.setScheEndDate(scheduleDTO.getScheEndDate());
//        schedule.setScheduleType(scheduleDTO.getScheduleType());
//        schedule.setLocation(scheduleDTO.getLocation());
//        schedule.setNotes(scheduleDTO.getNotes());
//        schedule.setEmpId(scheduleDTO.getEmpId());
//
//        scheduleService.saveSchedule(schedule);
//
//        return "redirect:/schedule";
//    }

}