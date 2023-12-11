package org.kosta.starducks.header.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileEditController {

  @GetMapping("/profileEdit")
  public String profileEdit() {
    return "header/profileEdit";
  }
}
