package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/mypage/attendance")
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
    @GetMapping
    public String Attendance(Model model, @AuthenticationPrincipal CustomUserDetails details) {
        // 로그인 안했을 때 임의의 아이디
        Long empId = 1L;

        // 로그인 했을 때 유저 정보 받아오기
        if(details != null) {
            empId = details.getEmployee().getEmpId();
        }
        model.addAttribute("empId", empId);

        Attendance attendanceForToday = attendanceService.getAttendanceForToday(empId);
        model.addAttribute("attendance", attendanceForToday);

        List<Attendance> attendanceForMonth = attendanceService.getAttendanceForMonth(empId);
        int absentDays =  (int)attendanceForMonth.stream()
                .filter(day -> day.getStartTime() == null && !day.isVacation())
                .count();
        int workDays = (int)attendanceForMonth.stream().count() - absentDays;
        int lateDays = attendanceService.getLateDays(empId); //오전 9시 이후 출근 시 지각
        int vacDays = (int)attendanceForMonth.stream()
                .filter(day -> day.isVacation())
                .count();

//        int workDays = attendanceService.getWorkDays(empId);
//        int absentDays = attendanceService.getAbsentDays(empId);

        model.addAttribute("workDays", workDays);
        model.addAttribute("lateDays", lateDays);
        model.addAttribute("absentDays", absentDays);

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
        return "redirect:/mypage/attendance";
    }

    /**
     * 퇴근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/endSubmit")
    public String submitEndTime() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달 - 테스트 empId = 1
        Long empId = 1L;
        attendanceService.saveEndTime(empId);
        return "redirect:/mypage/attendance";
    }

    /**
     * 출근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/startSubmitHome")
    public String submitStartTimeHome() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달 - 테스트 empId = 1
        Long empId = 1L;
        attendanceService.saveStartTime(empId);
        return "redirect:/";
    }

    /**
     * 퇴근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/endSubmitHome")
    public String submitEndTimeHome() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달 - 테스트 empId = 1
        Long empId = 1L;
        attendanceService.saveEndTime(empId);
        return "redirect:/";
    }
}