package org.kosta.starducks.hr.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.menus.MenuService;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.roles.Position;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hr/emp")
@RequiredArgsConstructor
public class EmpController {

    private final EmpService service;
    private final EmpRepository repository;
    private final HttpServletRequest request;
    private final DeptRepository deptRepository;

    /**
     * 인사부 홈 (사원 전체조회 및 검색)
     *
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model, EmpSearchCond empSearch) {
        MenuService.commonProcess(request, model, "hr");
        model.addAttribute("empSearch", empSearch);
        System.out.println(empSearch);

        Page<Employee> emps = service.toSearchEmp(empSearch);
        System.out.println(emps.stream().toList());
        model.addAttribute("employees", emps);


        return "hr/hrIndex";
    }

    /**
     * 사원 등록 get
     *
     * @param employee
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(@ModelAttribute Employee employee, Model model) {
        MenuService.commonProcess(request, model, "hr");
        // 자동으로 저장되는 사번을 미리 알려줌
        Long id = service.getLastEmpId();
        model.addAttribute("id", id + 1);
        model.addAttribute("positions", Position.values());
        model.addAttribute("depts", deptRepository.findAll());
        model.addAttribute("name", "register");
        return "hr/empWriter";
    }


    /**
     * 사원등록 post
     *
     * @param employee
     * @return
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee, Model model) {

        Employee savedEmp = service.saveEmp(employee);
//        model.addAttribute("result", true);
//        return ResponseEntity.ok("Employee information saved successfully.");

        Long id = savedEmp.getEmpId();

        return "redirect:/hr/emp/" + id;
    }

    /**
     * 개별 직원 조회
     *
     * @param empId
     * @param model
     * @return
     */
    @GetMapping("/{empId}")
    public String empDetail(@PathVariable("empId") Long empId, Model model) {
        MenuService.commonProcess(request, model, "hr");
        Employee employee = service.getEmp(empId);
        model.addAttribute("employee", employee);

        return "hr/empDetail";
    }

    /**
     * 직원 정보 수정
     *
     * @param empId 수정할 직원 사번
     * @param model
     * @return
     */
    @GetMapping("/edit/{empId}")
    public String empEdit (@PathVariable("empId") Long empId, Model model) {
        MenuService.commonProcess(request, model, "hr");
        Employee employee = service.getEmp(empId);
        model.addAttribute("employee", employee);
        model.addAttribute("positions", Position.values());
        model.addAttribute("depts", deptRepository.findAll());
        model.addAttribute("name", "edit");

        return "hr/empWriter";
    }
}

