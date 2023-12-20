package org.kosta.starducks.hr.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpFileService;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.repository.AttendanceRepository;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hr/attend")
@RequiredArgsConstructor
public class AttendController {

    private final EmpService empService;
    private final AttendanceService attendService;
    private final EmpFileService empFileService;
    private final AttendanceRepository attendanceRepository;


    @GetMapping
    private String index(Model model, EmpSearchCond empSearch) {
        model.addAttribute("empSearch", empSearch);

        Page<Employee> emps = empService.toSearchEmp(empSearch);
        model.addAttribute("employees", emps);

        return "hr/attend/index";
    }

    @GetMapping("/{empId}")
    private String details(@PathVariable("empId") Long empId, Model model) {

        /** 사원정보 */
        Employee employee = empService.getEmp(empId);
        model.addAttribute("employee", employee);

        /** 사원 프로필사진 */
        String profileImg = empFileService.getFileUrl(empId, "profile");
        model.addAttribute("profile", profileImg);

        /** 개인 근태 정보 */
        Attendance attendanceForToday = attendService.getAttendanceForToday(empId);
        model.addAttribute("attendance", attendanceForToday);

        int absentDays = attendService.getAbsentDays(empId);
        int workDays = attendService.getWorkDays(empId);
        int lateDays = attendService.getLateDays(empId);

        model.addAttribute("absentDays", absentDays);
        model.addAttribute("lateDays", lateDays);
        model.addAttribute("workDays", workDays);

        return "hr/attend/detail";
    }
}
