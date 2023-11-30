package org.kosta.starducks.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {
    @GetMapping("/attendance")
    public String Attendance(Model model) {
        model.addAttribute("data", "hello!!");
        return "mypage/attendance/attendance";
    }
}