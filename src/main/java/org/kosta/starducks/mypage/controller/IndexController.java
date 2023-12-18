package org.kosta.starducks.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.forum.service.ForumPostService;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class IndexController {

    private final AttendanceService attendService;
    private final EmpService empService;
    private final ForumPostService forumService;

    @GetMapping
    public String index(Model model){


        return "index";
    }

}
