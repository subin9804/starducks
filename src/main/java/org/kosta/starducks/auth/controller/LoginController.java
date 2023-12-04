package org.kosta.starducks.auth.controller;

import org.kosta.starducks.auth.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

//  로그인 페이지
  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error,
                      Model model) {
    if (error != null) {
      model.addAttribute("loginError", true);
    }
    return "auth/loginPage";
  }
//  //  로그인 페이지
//  @GetMapping("/login")
//  public String login() {
//            return "auth/loginPage1";
//  }

  // 비밀번호 찾기 페이지로 이동
  @GetMapping("/forgot-password")
  public String forgotPassword() {
    return "auth/forgotPasswordPage"; // 비밀번호 찾기 페이지의 뷰 이름을 입력합니다.
  }
}
