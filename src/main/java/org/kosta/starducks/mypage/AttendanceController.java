package org.kosta.starducks.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {
    @GetMapping("/mypage")
    public String Mypage(Model model) {
        model.addAttribute("data", "hello!!");
        return "mypage/index";
    }
}