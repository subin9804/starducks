package org.kosta.starducks.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.kosta.starducks.auth.service.EmailService;
import org.kosta.starducks.auth.service.UserService;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private PasswordEncoder passwordEncoder;


//  로그인 페이지
@GetMapping("/login")
public String login(@RequestParam(value = "error", required = false) String error,
                    @RequestParam(value = "exception", required = false) String exception,
                    Model model) {
  if (error != null && exception != null) {
    model.addAttribute("errorMessage", URLDecoder.decode(exception, StandardCharsets.UTF_8));
  }
  return "auth/login";
}

  // 비밀번호 찾기 페이지로 이동
  @GetMapping("/forgotPwd")
  public String forgotPassword() {
    return "auth/forgotPwd"; // 비밀번호 찾기 페이지의 뷰 이름을 입력합니다.
  }


  @PostMapping("/forgotPwd")
  public String processForgotPwd(@RequestParam("email") String email){
    // 이메일 주소에 해당하는 사용자 찾기
    Employee emp = userService.findByEmail(email);
    if (emp != null) {
    // 임시 비밀번호 생성 및 저장
    String tempPwd = userService.getTempPassword();
    emp.setPwd(passwordEncoder.encode(tempPwd));
    userService.updateEmp(emp);

    // 이메일 전송
      String subject = "비밀번호 재설정";
      String text = "귀하의 임시 비밀번호는 " + tempPwd + " 입니다. 로그인 후 비밀번호를 변경해 주세요.";
    emailService.sendTempPwdEmail(email, subject, text);
      // 리다이렉트 시 메시지 추가
    } else {
    }
    return "redirect:/login";
  }
}

