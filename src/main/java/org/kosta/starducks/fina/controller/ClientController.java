package org.kosta.starducks.fina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fina/client")
public class ClientController {

//    목록조회
    @GetMapping()
    public String clientList() {
        return "/fina/clientList";
    }


}
