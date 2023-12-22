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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/schedule")
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
    @GetMapping("/api/show")
    @ResponseBody
    public ResponseEntity<List<Schedule>> showSingleSchedule(Principal principal) {

        // 유저 정보 받아오기
        if (principal == null) {
            // 로그인되지 않은 경우 예외 처리 또는 다른 처리 방식을 선택할 수 있습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String empIdString = principal.getName();
        Long empId = Long.valueOf(empIdString);

        List<Schedule> scheduleList = scheduleService.findByEmployeeEmpId(empId);
        return ResponseEntity.ok(scheduleList);
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
            Schedule schedule = scheduleService.saveSchedule(scheduleDTO.toEntity());

            return ResponseEntity.ok(new ModelMapper().map(schedule, ScheduleDTO.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "일정 저장 중 오류 발생 ==> " + e.getMessage()));
        }
    }

    /**
     * 새로 등록된 일정 상세 조회
     *
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
     *
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
    public String deleteSchedule(@PathVariable("scheNo") Long scheNo, RedirectAttributes rttr) {
        scheduleService.deleteSchedule(scheNo, rttr);
        return "redirect:/mypage/schedule/show";
    }
}