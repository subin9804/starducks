package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.MenuService;
import org.kosta.starducks.mypage.service.ConfRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/conf")
public class ConfController {

    private final ConfRoomService service;
    private final HttpServletRequest request;

    @GetMapping
    public String index(Model model) {
        MenuService.commonProcess(request, model, "mypage");
        return "mypage/confRoom/confList";
    }
}
