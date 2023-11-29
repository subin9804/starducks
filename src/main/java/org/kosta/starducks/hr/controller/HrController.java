package org.kosta.starducks.hr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hr")
@RequiredArgsConstructor
public class HrController {

    @GetMapping
    public String index() {

        return "hr/index";
    }

}
