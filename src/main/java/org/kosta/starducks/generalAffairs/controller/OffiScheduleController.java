package org.kosta.starducks.generalAffairs.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.OffiSchedule;
import org.kosta.starducks.generalAffairs.service.OffiScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/general/schedule")
@RequiredArgsConstructor
public class OffiScheduleController {
    private final OffiScheduleService service;

    @GetMapping
    public String index(Model model) {

        return "generalAffairs/schedule";
    }


    /**
     * 전사 일정 정보 JSON
     * @param
     * @return
     */
    @GetMapping("/api/show")
    @ResponseBody
    public List<Map<String, Object>> showSingleSchedule(Model model) {
//        scheduleService를 통해 모든 일정을 가져옴

        List<OffiSchedule> scheduleList = service.getAll();
        System.out.println("스케쥴리스트" + scheduleList);

        // JSON 배열을 담을 리스트를 생성
        List<Map<String, Object>> scheduleDataList = new ArrayList<>();

        // 각 일정의 정보를 해시맵에 담고 JSON 객체로 변환하여 리스트에 추가
        for (OffiSchedule schedule : scheduleList) {
            HashMap<String, Object> scheduleData = new HashMap<>();
            scheduleData.put("title", schedule.getScheTitle());
            scheduleData.put("start", schedule.getScheStartDate());
            scheduleData.put("end", schedule.getScheEndDate());
            scheduleData.put("url", "/schedule/detailSche/" + schedule.getScheNo());
            // 리스트에 일정 정보를 추가
            scheduleDataList.add(scheduleData);
        }

        System.out.println("데이터리스트 : " + scheduleDataList);
        return scheduleDataList;
    }
}
