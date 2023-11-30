package org.kosta.starducks.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/sche")
    public String showSchedule() {
        return "mypage/schedule/Schedule";
    }

}