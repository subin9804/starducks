package org.kosta.starducks.hr.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptRepository repository;

    /**
     * 부서 전체조회
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model) {
        List<Department> depts = repository.findAll();
        model.addAttribute("depts", depts);

        return "hr/deptIndex";
    }

    @PostMapping("/add")
    public String addDept(@ModelAttribute Department department) {
        repository.saveAndFlush(department);

        return "redirect:/hr/dept";
    }

    /**
     * 각 부서별 사원정보 조회
     * @param deptId
     * @param model
     * @return
     */
    @GetMapping("/{deptId}")
    public String detail(@PathVariable("deptId") int deptId, Model model) {
        Department dept = repository.findById(deptId).orElse(null);
        System.out.println("======================================"+dept.getEmps().size());

        model.addAttribute("dept", dept);

        return "hr/deptIndex";
    }

    /**
     * 부서정보수정
     * @param deptId
     * @param dept
     * @return
     */
    @PostMapping("/update/{deptId}")
    public String update(@PathVariable("deptId") int deptId, Department dept) {
        Department originDept = repository.findById(deptId).orElse(null);

        originDept.setDeptName(dept.getDeptName());
        originDept.setDeptRepTel(dept.getDeptRepTel());

        repository.saveAndFlush(originDept);
        return "redirect:/hr/dept";
    }

    /**
     * 부서 삭제
     * @param deptId
     * @return
     */
    @GetMapping("/delete/{deptId}")
    public String delete(@PathVariable("deptId") int deptId) {
        Department dept = repository.findById(deptId).orElse(null);
        if(deptId < 6 && dept != null) {
            repository.delete(dept);
        }

        return "redirect:/hr/dept";
    }
}
