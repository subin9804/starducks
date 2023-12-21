package org.kosta.starducks.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * 시큐리티 컨피그에서 사용하는 용도. 로그인 실패 때 에러메시지 출력을 위한 핸들러
 * 시큐리티 사용 때문에 세션을 통해서 정보를 확인해야함
 */

@Component
public class CustomFailHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
                                      HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
    String errorMessage;
    if (exception instanceof BadCredentialsException) {
      errorMessage = "정보가 일치하지 않습니다.";

    } else {
      errorMessage = "로그인에 실패하였습니다. 관리자에게 문의하세요.";
    }

    if (exception instanceof DisabledException) {
      errorMessage = "퇴사한 직원은 로그인할 수 없습니다."; // 퇴사한 직원에 대한 메시지
    }
    errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
    setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);

    super.onAuthenticationFailure(request, response, exception);
  }
}
