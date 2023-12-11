package org.kosta.starducks;

import jakarta.servlet.http.HttpServletRequest;
import org.kosta.starducks.commons.menus.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public String index(Model model) {
        MenuService.commonProcess(request, model, "mypage");

        return "index";
    }
}
