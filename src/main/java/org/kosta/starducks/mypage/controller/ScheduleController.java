package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.commons.menus.MenuService;
import org.kosta.starducks.mypage.dto.ScheduleDTO;
import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final HttpServletRequest request;

    /**
     * 로그인을 한 사원의 일정 조회
     * <p>
     * 정보를 던져주는 용도의 GetMapping
     *
     * @param
     * @return
     */
    @GetMapping("/show/{empId}")
    @ResponseBody
    public List<Map<String, Object>> showSingleSchedule(@PathVariable("empId") Long empId, Model model) {
        // scheduleService를 통해 모든 일정을 가져옴

//        System.out.println("아이디!!!!!" + empId);
        List<Schedule> scheduleList = scheduleService.findByEmployeeEmpId(empId);
//        System.out.println("스케쥴리스트" + scheduleList);
        // JSON 배열을 담을 리스트를 생성
        List<Map<String, Object>> scheduleDataList = new ArrayList<>();
        // 각 일정의 정보를 해시맵에 담고 JSON 객체로 변환하여 리스트에 추가
        for (Schedule schedule : scheduleList) {
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

    /**
     * 화면 조회를 위한 GetMapping
     *
     * @param model
     * @return
     */
    @GetMapping("/show")
    public String showSchedule(Model model) {
        MenuService.commonProcess(request, model, "mypage");
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        model.addAttribute("scheduleDTO", scheduleDTO);
        return "mypage/schedule/schedule";
    }

    /**
     * 일정 등록하기
     *
     * @param scheduleDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = scheduleDTO.toEntity();
            schedule = scheduleService.saveSchedule(schedule); // 새로 저장된 Schedule 객체를 반환받음

            ModelMapper modelMapper = new ModelMapper();
            ScheduleDTO responseDTO = modelMapper.map(schedule, ScheduleDTO.class); // Schedule 객체를 ScheduleDTO로 매핑

            return ResponseEntity.ok(responseDTO); // ScheduleDTO로 응답
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "일정 저장 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 새로 등록된 일정 상세 조회
     * @param model
     * @param scheNo
     * @return
     */
    @GetMapping("/detail/{scheNo}")
    public String findScheduleDetail(Model model, @PathVariable("scheNo") Long scheNo) {
        ScheduleDTO detailSchedule = scheduleService.findScheduleDetail(scheNo);
        model.addAttribute("detailSchedule", detailSchedule);

        return "mypage/schedule/scheduleDetail";
    }

    /**
     * 기존에 등록되어 있던 일정 상세조회(DB 데이터 상세조회)
     * @param model
     * @param scheNo
     * @return
     */
    @GetMapping("/detailSche/{scheNo}")
    public String findScheduleDetails(Model model, @PathVariable("scheNo") Long scheNo) {
        ScheduleDTO detailSchedule = scheduleService.findScheduleDetail(scheNo);
        model.addAttribute("detailSchedule", detailSchedule);

        return "mypage/schedule/scheduleDetail";
    }

    @GetMapping("/delete/{scheNo}")
    public ResponseEntity<?> deleteSchedule(@PathVariable("scheNo") Long scheNo) {
        try {
            scheduleService.deleteSchedule(scheNo);
            return ResponseEntity.ok().body("일정이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("오류 발생: " + e.getMessage());
        }
    }
}