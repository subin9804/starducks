package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.kosta.starducks.mypage.entity.Attendance;

import org.kosta.starducks.commons.MenuService;

import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final HttpServletRequest request;

    /**
     * 일별 출근 정보 Json List API
     */
    @GetMapping("/daily/{empId}") // json api endPoint
    @ResponseBody
    public List<Map<String, Object>> getDailyAttendance(@PathVariable("empId") Long empId, Model model) {
        return attendanceService.getDailyAttendance(empId);
    }

    /**
     * 마이페이지 근태기록 컨트롤러 함수
     */
    @GetMapping("/{empId}")
    public String Attendance(Model model, @PathVariable("empId") Long empId) {
        MenuService.commonProcess(request, model, "mypage");
        Attendance attendanceForToday = attendanceService.getAttendanceForToday(empId);
        model.addAttribute("attendance", attendanceForToday);

        List<Attendance> attendanceForMonth = attendanceService.getAttendanceForMonth(empId);
        int absentDays =  (int)attendanceForMonth.stream()
                .filter(Objects::isNull)
                .count();
        int workDays = (int)attendanceForMonth.stream().count() - absentDays;
        model.addAttribute("absentDays", absentDays);
        model.addAttribute("workDays", workDays);
//        model.addAttribute("isVacation", attendanceService.getIsVacation);

        return "mypage/attendance/attendance";
    }

    /**
     * 출근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/startSubmit")
    public String submitStartTime() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달 - 테스트 empId = 1
        Long empId = 1L;
        attendanceService.saveStartTime(empId);
        return "mypage/attendance/startSuccess";
    }

    /**
     * 퇴근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/endSubmit")
    public String submitEndTime() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달 - 테스트 empId = 1
        Long empId = 1L;
        attendanceService.saveEndTime(empId);
        return "mypage/attendance/endSuccess";
    }
}