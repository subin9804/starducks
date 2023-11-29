package org.kosta.starducks;

import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestCont {

@Autowired
private EmpRepository repository;

    @GetMapping("/auth")
    public String main() {
        return "hr/index";
    }


}