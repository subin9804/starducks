package org.kosta.starducks;

import jakarta.servlet.http.HttpServletRequest;
import org.kosta.starducks.commons.menus.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/test")
    public String index(Model model) {
        MenuService.commonProcess(request, model, "mypage");

        return "index";
    }
}
