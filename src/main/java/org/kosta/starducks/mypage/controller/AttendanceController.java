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

import java.security.Principal;
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

//    /**
//     * 일별 출근 정보 Json List API
//     */
//    @GetMapping("/daily") // json api endPoint
//    @ResponseBody
//    public List<Map<String, Object>> getDailyAttendance(@AuthenticationPrincipal CustomUserDetails details, Model model) {
//        // 로그인 안했을 때 임의의 아이디
//        Long empId = 1L;
//
//        // 로그인 했을 때 유저 정보 받아오기
//        if(details != null) {
//            empId = details.getEmployee().getEmpId();
//        }
//
//        return attendanceService.getDailyAttendance(empId);
//    }

    /**
     * 마이페이지 근태기록 컨트롤러 함수
     */
    @GetMapping
    public String Attendance(Model model, Principal principal) {
        Long empId = Long.parseLong(principal.getName());

        model.addAttribute("empId", empId);

        Attendance attendanceForToday = attendanceService.getAttendanceForToday(empId);
        model.addAttribute("attendance", attendanceForToday);

        List<Attendance> attendanceForMonth = attendanceService.getAttendanceForMonth(empId);
        int absentDays =  (int)attendanceForMonth.stream()
                .filter(Objects::isNull)
                .count();
        int workDays = (int)attendanceForMonth.stream().count() - absentDays;

//        int absentDays = attendanceService.getAbsentDays(empId);
//        int workDays = attendanceService.getWorkDays(empId);

        model.addAttribute("absentDays", absentDays);
        model.addAttribute("workDays", workDays);
//        model.addAttribute("isVacation", attendanceService.getIsVacation);

        return "mypage/attendance/attendance";
    }



    /**
     * 출근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/startSubmit")
    public String submitStartTime(Principal principal) {
        Long empId = Long.parseLong(principal.getName());
        attendanceService.saveStartTime(empId);
        return "mypage/attendance/startSuccess";
    }

    /**
     * 퇴근 기록 & 성공 페이지 리턴 함수
     */
    @PostMapping("/endSubmit")
    public String submitEndTime(Principal principal) {
        Long empId = Long.parseLong(principal.getName());
        attendanceService.saveEndTime(empId);
        return "mypage/attendance/endSuccess";
    }
}