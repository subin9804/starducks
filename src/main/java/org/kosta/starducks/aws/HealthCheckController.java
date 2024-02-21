package org.kosta.starducks.aws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {

  /**
   * green blue 어떤 서버가 활성화 상태인지 확인하는 용도
   *
   */
  @GetMapping("/hc")
  public ResponseEntity<?> healthCheck() {
    Map<String, String> responseData = new HashMap<>();

    return ResponseEntity.ok(responseData);
  }

}
