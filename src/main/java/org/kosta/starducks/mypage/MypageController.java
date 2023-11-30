package org.kosta.starducks.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
    @GetMapping("/mypage")
    public String mypage() {
        return "mypage/main";
    }
}