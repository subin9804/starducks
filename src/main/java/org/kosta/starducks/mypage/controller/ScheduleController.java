package org.kosta.starducks.mypage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.mypage.dto.ScheduleDTO;
import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 로그인을 한 사원의 일정 조회
     *
     * 정보를 던져주는 용도의 GetMapping
     * @param
     * @return
     */
    @GetMapping("/show/{empId}")
    @ResponseBody
    public List<Map<String, Object>> showSingleSchedule(@PathVariable("empId") Long empId, Model model) {
        // scheduleService를 통해 모든 일정을 가져옵니다.
        List<Schedule> scheduleList = scheduleService.findByEmployeeEmpId(empId);

        // JSON 배열을 담을 리스트를 생성합니다.
        List<Map<String, Object>> scheduleDataList = new ArrayList<>();

        // 각 일정의 정보를 해시맵에 담고 JSON 객체로 변환하여 리스트에 추가합니다.
        for (Schedule schedule : scheduleList) {
            HashMap<String, Object> scheduleData = new HashMap<>();
            scheduleData.put("scheNo", schedule.getScheNo());
            scheduleData.put("scheTitle", schedule.getScheTitle());
            scheduleData.put("scheStartDate", schedule.getScheStartDate());
            scheduleData.put("scheEndDate", schedule.getScheEndDate());
            scheduleData.put("scheduleType", schedule.getScheduleType().toString());
            scheduleData.put("location", schedule.getLocation().toString());
            scheduleData.put("notes", schedule.getNotes());
            scheduleData.put("empId", schedule.getEmployee().getEmpId());

            // 리스트에 일정 정보를 추가합니다.
            scheduleDataList.add(scheduleData);
        }

        // 모델에 데이터를 담아 화면으로 전달합니다.
        model.addAttribute("scheduleDataList", scheduleDataList);

        // 화면으로 이동합니다.
        return scheduleDataList;
    }

    /**
     * 화면 조회를 위한 GetMapping
     * @param model
     * @return
     */
    @GetMapping("/show")
    public String showSchedule(Model model) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        model.addAttribute("scheduleDTO", scheduleDTO);
        return "mypage/schedule/schedule";
    }

    @PostMapping("/schedule")
    public String addSchedule(ScheduleDTO scheduleDTO) {
        System.out.println("scheduleDTO.toString() ==> " + scheduleDTO.toString()); /** DTO에 폼 데이터가 잘 담겼는지 확인*/
        return "redirect:/mypage/schedule/registModal";
    }
}