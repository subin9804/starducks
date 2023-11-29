package org.kosta.starducks.hr.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.entity.EmpEntity;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hr")
@RequiredArgsConstructor
public class HrController {

    private final EmpService service;
    private final EmpRepository repository;

    @GetMapping
    public String index(Model model) {
        EmpEntity emp = service.getEmp(1L);
//        List<EmpEntity> emps = service.getAllEmp();

//        System.out.println(emps.size());
//        model.addAttribute("emps", emps);

//        EmpEntity emp = service.getEmp(1L);
//        System.out.println(emp);

        return "hr/index";
    }

}
