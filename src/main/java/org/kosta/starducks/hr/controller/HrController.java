package org.kosta.starducks.hr.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.roles.Position;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hr")
@RequiredArgsConstructor
public class HrController {

    private final EmpService service;
    private final EmpRepository repository;

    @GetMapping
    public String index(Model model) {
        System.out.println(service.getAllEmp().size());
        System.out.println(service.getLastEmpId());

        return "hr/index";
    }

    @GetMapping("/register")
    public String register (@ModelAttribute Employee employee, Model model) {
        // 자동으로 저장되는 사번을 미리 알려줌
        Long id = service.getLastEmpId();
        model.addAttribute("id", id + 1);
        model.addAttribute("position", Position.values());

        return "hr/empRegister";
    }

    @PostMapping("/register")
    public String registerPs (@ModelAttribute Employee employee) {
        service.saveEmp(employee);

        return "redirect:/hr/register";
    }
}
