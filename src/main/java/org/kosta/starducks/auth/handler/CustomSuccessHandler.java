package org.kosta.starducks.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private PasswordEncoder passwordEncoder;

    public CustomSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        String username = details.getUsername();
        String password = details.getPassword();

        String initialPassword = "PASS_" + username;

        Boolean matched = passwordEncoder.matches(initialPassword, password);

        if (password != null && matched) {
            Cookie cookie = new Cookie("haveToChange", "true");
            cookie.setPath("/");
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + "/profileEdit");
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
