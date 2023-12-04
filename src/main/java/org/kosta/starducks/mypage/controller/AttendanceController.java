package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.MenuService;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final HttpServletRequest request;

    @GetMapping("/daily/{empId}")
    @ResponseBody
    public List<Map<String, Object>> getDailyAttendance(@PathVariable("empId") Long empId, Model model) {
        MenuService.commonProcess(request, model, "mypage");
        return attendanceService.getDailyAttendance(empId);
    }

    @GetMapping
    public String Attendance(Model model) {
        MenuService.commonProcess(request, model, "mypage");
        model.addAttribute("data", "hello!!");
        return "mypage/attendance/attendance";
    }

    @GetMapping("/form")
    public String showAttendanceForm(Model model) {
        MenuService.commonProcess(request, model, "mypage");
        return "mypage/attendance/form";
    }

    @PostMapping("/startSubmit")
    public String submitStartTime() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달
        Long empId = 1L; // 예제에서는 EmpId를 1로 가정
        attendanceService.saveStartTime(empId);
        return "mypage/attendance/startSuccess";
    }

    @PostMapping("/endSubmit")
    public String submitEndTime() {
        // 사용자의 EmpId는 세션 등에서 가져오거나 매개변수 등을 통해 전달
        Long empId = 1L; // 예제에서는 EmpId를 1로 가정
        attendanceService.saveEndTime(empId);
        return "mypage/attendance/endSuccess";
    }

//    @GetMapping("/daily/{empId}")
//    public List<AttendanceDTO> getDailyAttendance(@PathVariable Long empId) {
//        // AttendanceService를 통해 일별 출근 정보를 조회
//        List<AttendanceDTO> dailyAttendanceList = attendanceService.getDailyAttendance(empId);
//        return dailyAttendanceList;
//    }
}