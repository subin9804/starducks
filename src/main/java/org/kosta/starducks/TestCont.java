package org.kosta.starducks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestCont {

    @GetMapping("/auth")
    public String main() {
        return "hr/index";
    }
}