package org.kosta.starducks.hr.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.kosta.starducks.hr.service.AlreadyExistDeptException;
import org.kosta.starducks.hr.service.EmpFileService;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hr/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptRepository repository;
    private final EmpService empService;
    private final EmpFileService fileService;
    private final HttpServletResponse response;

    /**
     * 부서 전체조회
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model, @RequestParam(name = "page", required = false) Integer page, Department newDept) {
        if(page == null || page == 0) {
            page = 1;
        }

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Department> depts = repository.findAll(pageable);

        int startPage= Math.max(page-4, 1);
        int endPage= Math.min(page+5, depts.getTotalPages());

        model.addAttribute("nowPage", page);
        model.addAttribute("depts", depts.getContent());
        model.addAttribute("totalPages", depts.getTotalPages());
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("newDept", newDept);

        return "hr/dept/deptIndex";
    }

    /**
     * 새 부서 등록
     * @param department
     * @return
     */
    @PostMapping("/add")
    public String addDept(Department department) {
        if(repository.existsByDeptId(department.getDeptId())) {
            throw new AlreadyExistDeptException("dept.error.alreadyExists");
        }

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
    public String detail(@PathVariable("deptId") int deptId, Model model, @RequestParam(name = "name", required = false) String name) {
        // 이름으로 사원 조회
        Department dept = repository.findById(deptId).orElse(null);
        List<Employee> emps = null;

        if(name != null && !name.isBlank() && name != "") { // 검색창에 글자가 있는 경우
            emps = empService.getEmp(deptId, name);

        } else {
            emps = empService.getEmp(deptId);
        }

        List<Employee> runningEmp = emps.stream().filter(item -> item.isStatus() == false).toList();
        List<Employee> stoppedEmp = emps.stream().filter(item -> item.isStatus() == true).toList();

        model.addAttribute("dept", dept);
        model.addAttribute("running", runningEmp);
        model.addAttribute("stopped", stoppedEmp);

        // 사원 프로필 이미지
        Map<Long, String> profiles = new HashMap();
        for(Employee emp : emps) {
            String profile = fileService.getFileUrl(emp.getEmpId(), "profile");
            profiles.put(emp.getEmpId(), profile);

        }
        model.addAttribute("profiles", profiles);

        return "hr/dept/deptDetail";
    }

    /**
     * 부서정보수정
     * @param dept
     * @return
     */
    @PostMapping("/edit")
    public String edit(Department dept) {
        Department originDept = repository.findById(dept.getDeptId()).orElse(null);

        originDept.setDeptName(dept.getDeptName());
        originDept.setDeptRepTel(dept.getDeptRepTel());

        repository.saveAndFlush(originDept);

        return "redirect:/hr/dept/" + dept.getDeptId();
    }

    /**
     * 부서 삭제
     * @param deptId
     * @return
     */
    @GetMapping("/delete/{deptId}")
    public String delete(@PathVariable("deptId") int deptId) {
        Department dept = repository.findById(deptId).orElse(null);
        List<Employee> emps = empService.getEmp(deptId);

        if(deptId < 6 && dept != null) {
            System.out.println("삭제 불가" + dept.getDeptName());
        } else {
            // 해당되는 직원이 한 명도 없는 경우
            if(dept.getEmps().size() <= 0 || dept.getEmps() == null || emps.size() <= 0 || emps == null) {
                repository.delete(dept);
            }

            // 속해있는 직원이 모두 퇴사한 경우
            boolean canDelete = true;

            for(Employee emp : emps) {
                if(emp.isStatus() == false) {
                    canDelete = false;
                }
            }

            if(canDelete) {
                // 삭제할 부서의 직원들 (퇴사한 직원들)의 부서를 'No_Dept'로 표시한다.
                Department _noDept = Department.builder()
                        .deptId(0)
                        .deptName("해당 없음")
                        .deptRepTel("")
                        .build();

                Department noDept = repository.findById(0).orElse(_noDept);


                for(Employee emp : emps) {
                    if(emp.isStatus() == true) {
                        emp.setDept(noDept);
                    }
                }

            }

        }

        return "redirect:/hr/dept";
    }

}
