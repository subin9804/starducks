package org.kosta.starducks.header.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.header.dto.NewPasswordDto;
import org.kosta.starducks.header.dto.PasswordDto;
import org.kosta.starducks.header.service.ProfileService;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class ProfileController {

  @Autowired
  private ProfileService profileService;


  // 현재 로그인한 사용자의 프로필 정보를 불러오는 메소드
  @GetMapping("/profileEdit")
  public String profileEdit(Model model, Principal principal, HttpServletRequest request) {
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    if (csrfToken != null) {
      model.addAttribute("_csrf", csrfToken);
    }

    Long empId = Long.parseLong(principal.getName()); // 로그인한 사용자의 ID
    Employee employee = profileService.getEmployeeById(empId); // 사원 정보 검색
    model.addAttribute("employee", employee);

    return "header/profileEdit";
  }

  // 사용자가 수정한 프로필 정보를 처리하는 메소드
  @PostMapping("/profileEdit/update")
  public String updateEmployee(Employee employee, Principal principal) {
    profileService.updateProfile(employee); // ProfileService를 통해 프로필 업데이트 처리

    // 새로운 정보 다시 로드
    Long empId = Long.parseLong(principal.getName()); // 로그인한 사용자의 ID
    Employee updatedEmployee = profileService.getEmployeeById(empId); // 사원 정보 재검색
    if (updatedEmployee != null) {
      // 현재 Authentication 객체 가져오기
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      // 새로운 CustomUserDetails 객체 생성
      CustomUserDetails newCustomUserDetails = new CustomUserDetails(updatedEmployee);

      // 새로운 Authentication 객체 생성
      Authentication newAuth = new UsernamePasswordAuthenticationToken(newCustomUserDetails, authentication.getCredentials(), newCustomUserDetails.getAuthorities());

      // SecurityContext에 새 Authentication 객체 설정
      SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    return "redirect:/profileEdit"; // 처리 후 리디렉트할 페이지
  }


  //현재 비밀번호 확인
  @PostMapping("/profileEdit/checkPassword")
  @ResponseBody
  public boolean checkCurrentPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestBody PasswordDto passwordDto) {
    Employee employee = userDetails.getEmployee();
    return profileService.checkCurrentPassword(passwordDto.getCurrentPassword(), employee);
  }

  //새로운 비밀번호로 변경하기
  @ResponseBody
  @PostMapping("/profileEdit/updatePassword")
  public boolean updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestBody NewPasswordDto newPasswordDto) {
    Employee employee = userDetails.getEmployee();
    return profileService.updatePassword(newPasswordDto.getNewPassword(), employee);
  }
}