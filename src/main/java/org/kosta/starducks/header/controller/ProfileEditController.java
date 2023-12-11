package org.kosta.starducks.header.controller;

import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProfileEditController {

  @Autowired
  private EmpService empService;

  // 현재 로그인한 사용자의 프로필 정보를 불러오는 메소드ㅇ
  @GetMapping("/profileEdit")
  public String profileEdit(Model model, Principal principal) {
    Long empId = Long.parseLong(principal.getName());
    Employee employee = empService.getEmp(empId);
    model.addAttribute("employee", employee);
    return "header/profileEdit";
  }

  // 사용자가 수정한 프로필 정보를 처리하는 메소드
  @PostMapping("/hr/emp/update")
  public String updateEmployee(@ModelAttribute Employee employee) {
    empService.saveEmp(employee);
    return "redirect:/profileEdit"; // 처리 후 리디렉트할 페이지
  }
}
