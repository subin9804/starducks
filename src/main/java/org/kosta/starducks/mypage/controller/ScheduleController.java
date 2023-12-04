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
    public String showSchedule(Model model) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        model.addAttribute("scheduleDTO", scheduleDTO);
        return "mypage/schedule/registModal2";
    }

    @PostMapping("/schedule")
    public String scheduleTest(ScheduleDTO scheduleDTO) {
        System.out.println("scheduleDTO.toString() ==> " + scheduleDTO.toString()); /** DTO에 폼 데이터가 잘 담겼는지 확인*/

        return "redirect:/mypage/schedule/schedule";
    }

}