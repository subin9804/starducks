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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hr/attend")
@RequiredArgsConstructor
public class AttendController {

    private final EmpService empService;
    private final AttendanceService attendService;
    private final EmpFileService empFileService;
    private final AttendanceRepository attendanceRepository;


    @GetMapping
    private String index(@RequestParam(name = "page", required = false) Integer page, Model model, EmpSearchCond empSearch) {
        if(page == null || page == 0) {
            page = 1;
        }

        model.addAttribute("empSearch", empSearch);

        Page<Employee> emps = empService.toSearchEmp(empSearch);

        int startPage= Math.max(page-4, 1);
        int endPage= Math.min(page+5, emps.getTotalPages());

        // 페이지네이션
        model.addAttribute("nowPage", page);
        model.addAttribute("employees", emps.getContent());
        model.addAttribute("totalPages", emps.getTotalPages());
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        // 오늘 근태
        Map<Long, Attendance> attendanceMap = new HashMap<Long, Attendance>();
        for(Employee emp : emps) {
            Attendance attendanceForToday = attendService.getAttendanceForToday(emp.getEmpId());
            attendanceMap.put(emp.getEmpId(), attendanceForToday);
        }

        model.addAttribute("attend", attendanceMap);

        return "hr/attend/index";
    }

    /**
     * 근태관리 상세페이지
     * @param empId
     * @param model
     * @return
     */
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
