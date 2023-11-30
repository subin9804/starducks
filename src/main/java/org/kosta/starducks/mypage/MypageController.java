package org.kosta.starducks.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
    @GetMapping("/mypage")
    public String mypage(Model model) {
        model.addAttribute("data", "hello!!");
        return "mypage/index";
    }
}