package org.kosta.starducks.header.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.header.service.ProfileService;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

  @Autowired
  private ProfileService profileService;

  // 현재 로그인한 사용자의 프로필 정보를 불러오는 메소드
  @GetMapping("/profileEdit")
  public String profileEdit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    if (csrfToken != null) {
      model.addAttribute("_csrf", csrfToken);
    }
    Employee employee = userDetails.getEmployee();
    model.addAttribute("employee", employee);
    return "header/profileEdit";
  }

  // 사용자가 수정한 프로필 정보를 처리하는 메소드
  @PostMapping("/profileEdit/update")
  public String updateEmployee(@ModelAttribute Employee employee) {
    profileService.updateProfile(employee); // ProfileService를 통해 프로필 업데이트 처리
    return "redirect:/profileEdit"; // 처리 후 리디렉트할 페이지
  }

  @ResponseBody
  @PostMapping("/profileEdit/checkPassword")
  public boolean checkCurrentPassword(@RequestParam Long empId, @RequestParam String currentPassword) {
    return profileService.checkCurrentPassword(empId, currentPassword);
  }

  @ResponseBody
  @PostMapping("/profileEdit/updatePassword")
  public boolean updatePassword(@RequestParam Long empId, @RequestParam String newPassword) {
    return profileService.updatePassword(empId, newPassword);
  }
}
