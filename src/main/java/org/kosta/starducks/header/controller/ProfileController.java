package org.kosta.starducks.header.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.header.dto.NewPasswordDto;
import org.kosta.starducks.header.dto.PasswordDto;
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
  public String updateEmployee(@ModelAttribute Employee employee, @AuthenticationPrincipal CustomUserDetails userDetails) {
    profileService.updateProfile(employee); // ProfileService를 통해 프로필 업데이트 처리

    // 새로운 정보 다시 로드
    Employee updatedEmployee = profileService.getEmployeeById(employee.getEmpId());
//    Employee updatedEmployee = userDetails.getEmployee(); 이건 번경 전 정보를 다시 덮는 거여서 안됨. 위처럼 해야함
    if (updatedEmployee != null) {
      userDetails.setEmployee(updatedEmployee); // 세션 정보 업데이트 (필요한 경우)
    }

    return "redirect:/profileEdit"; // 처리 후 리디렉트할 페이지
  }

  @PostMapping("/profileEdit/checkPassword")
  @ResponseBody
  public boolean checkCurrentPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestBody PasswordDto passwordDto) {
    Employee employee = userDetails.getEmployee();
    return profileService.checkCurrentPassword(passwordDto.getCurrentPassword(), employee);
  }

  @ResponseBody
  @PostMapping("/profileEdit/updatePassword")
  public boolean updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestBody NewPasswordDto newPasswordDto) {
    Long empId = Long.valueOf(userDetails.getUsername());
    return profileService.updatePassword(empId, newPasswordDto.getNewPassword());
  }
}
