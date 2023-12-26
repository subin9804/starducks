package org.kosta.starducks.hr.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.kosta.starducks.hr.repository.EmpFileRepository;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpFileService;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.roles.Position;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/hr/emp")
@RequiredArgsConstructor
public class EmpController {

    private final EmpService service;
    private final EmpRepository repository;
    private final HttpServletRequest request;
    private final DeptRepository deptRepository;
    private final EmpFileService fileService;
    private final EmpFileRepository fileRepository;

    /**
     * 인사부 홈 (사원 전체조회 및 검색)
     *
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model, EmpSearchCond empSearch) {
        model.addAttribute("empSearch", empSearch);
        System.out.println(empSearch);

        Page<Employee> emps = service.toSearchEmp(empSearch);
        System.out.println(emps.stream().toList());
        model.addAttribute("employees", emps);

        return "hr/emp/hrIndex";
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
        // 자동으로 저장되는 사번을 미리 알려줌
        Long id = service.getLastEmpId();
        model.addAttribute("id", id + 1);
        model.addAttribute("positions", Position.values());
        model.addAttribute("depts", deptRepository.findAll());
        model.addAttribute("name", "register");
        return "hr/emp/empWriter";
    }


    /**
     * 사원등록 및 수정 post
     *
     * @param employee
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid Employee employee, BindingResult result, Model model,
                       @RequestParam("profile") MultipartFile profile,
                       @RequestParam("stamp") MultipartFile stamp) {

        if (result.hasErrors()) {
//            log.error("result: {}", result.getAllErrors().toString());
            if(employee.getEmpId() != null) {
                model.addAttribute("name", "edit");
                model.addAttribute("positions", Position.values());
                model.addAttribute("depts", deptRepository.findAll());
            } else {
                // 자동으로 저장되는 사번을 미리 알려줌
                Long id = service.getLastEmpId();
                model.addAttribute("id", id + 1);
                model.addAttribute("positions", Position.values());
                model.addAttribute("depts", deptRepository.findAll());
                model.addAttribute("name", "register");
            }
            return "hr/emp/empWriter";
        }

        Employee savedEmp = service.saveEmp(employee);
        fileService.upload(profile, "profile", employee.getEmpId());
        fileService.upload(stamp, "stamp", employee.getEmpId());

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
        Employee employee = service.getEmp(empId);
        model.addAttribute("employee", employee);

        // 파일
        String profile = fileService.getFileUrl(empId, "profile");
        String stamp = fileService.getFileUrl(empId, "stamp");

        model.addAttribute("profile", profile);
        model.addAttribute("stamp", stamp);


        return "hr/emp/empDetail";
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
        Employee employee = service.getEmp(empId);
        model.addAttribute("employee", employee);
        model.addAttribute("positions", Position.values());
        model.addAttribute("depts", deptRepository.findAll());
        model.addAttribute("name", "edit");

        // 파일
        String profile = fileService.getFileUrl(empId, "profile");
        String stamp = fileService.getFileUrl(empId, "stamp");

        model.addAttribute("profile", profile);
        model.addAttribute("stamp", stamp);

        return "hr/emp/empWriter";
    }
}

