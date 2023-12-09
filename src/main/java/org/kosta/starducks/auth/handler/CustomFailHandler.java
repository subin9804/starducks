package org.kosta.starducks.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomFailHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
                                      HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
    String errorMessage;
    if (exception instanceof BadCredentialsException) {
      errorMessage = "일치하는 정보가 없습니다.";
    } else {
      errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
    }
    errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
    setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);

    super.onAuthenticationFailure(request, response, exception);
  }
}
