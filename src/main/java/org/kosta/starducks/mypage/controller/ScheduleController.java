package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
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
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/schedule")

public class ScheduleController {
    private final ScheduleService scheduleService;
    private final EmpService empService;
    private final HttpServletRequest request;

    /**
     * 로그인한 사용자의 일정 정보를 가져오는 데 사용
     * @param principal
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

        Long empId = Long.parseLong(principal.getName());
//        log.info("누가 로그인했니!!!!!!!!! ==> " + empId);

        List<Schedule> scheduleList = scheduleService.findByEmployeeEmpId(empId);

        return ResponseEntity.ok(scheduleList);
    }

    /**
     * 사용자의 일정 화면을 표시
     * @param model
     * @param principal
     * @return
     */
    @GetMapping("/show")
    public String showSchedule(Model model, Principal principal) {

        Long empId = Long.parseLong(principal.getName());

        model.addAttribute("empId", empId);
        return "mypage/schedule/schedule";
    }

    /**
     * 새로운 일정을 등록하는 데 사용
     * JSON 형식의 데이터를 요청으로 받아 새 일정을 생성하고, 생성된 일정을 반환
     * @param scheduleDTO
     * @param principal
     * @return
     */
    @PostMapping("/add")
    public RedirectView addSchedule(@ModelAttribute ScheduleDTO scheduleDTO, Principal principal, RedirectAttributes ra) {
        try {
            if (principal == null) {
                // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
                return new RedirectView("/login");
            }
            Long empId = Long.parseLong(principal.getName());

            Employee employee = empService.getEmpById(empId);

            ModelMapper modelMapper = new ModelMapper();
            Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
            schedule.setEmployee(employee);

            Schedule savedSchedule = scheduleService.saveSchedule(schedule);
            ScheduleDTO savedScheduleDTO = modelMapper.map(savedSchedule, ScheduleDTO.class);

            // 리다이렉트 시에 데이터를 넘기고 싶다면 RedirectAttributes를 사용
            ra.addFlashAttribute("message", "일정이 성공적으로 추가되었습니다.");

            // 리다이렉트할 경로를 반환
            return new RedirectView("/mypage/schedule/show");
        } catch (Exception e) {
            log.error("일정 저장 중 오류 발생 ==> " + e.getMessage(), e);
            // 에러가 발생한 경우 에러 페이지로 리다이렉트 또는 다른 처리를 수행
            return new RedirectView("/error");
        }
    }

    /**
     * 새로 등록된 일정 또는 기존에 등록된 일정의 상세 정보를 조회
     * {scheNo} 경로 변수를 통해 특정 일정을 식별하고, 해당 일정의 상세 정보를 반환
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
     * 상세한 정보를 조회하는 데 사용
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

    /**
     * 특정 일정을 삭제하는 데 사용
     * {scheNo} 경로 변수를 통해 삭제할 일정을 식별하고, 삭제 후 일정 목록 화면으로 리다이렉트
     * @param scheNo
     * @param rttr
     * @return
     */
    @GetMapping("/delete/{scheNo}")
    public String deleteSchedule(@PathVariable("scheNo") Long scheNo, RedirectAttributes rttr) {
        scheduleService.deleteSchedule(scheNo, rttr);
        return "redirect:/mypage/schedule/show";
    }
}