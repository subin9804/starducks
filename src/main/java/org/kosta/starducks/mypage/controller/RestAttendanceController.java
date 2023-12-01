package org.kosta.starducks.mypage.controller;

import org.kosta.starducks.mypage.dto.AttendanceDto;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class RestAttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/daily/{empId}")
    @ResponseBody
    public List<Map<String, Object>> getDailyAttendance(@PathVariable Long empId) {
        return attendanceService.getDailyAttendance(empId);
    }
}