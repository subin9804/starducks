package org.kosta.starducks.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kosta.starducks.auth.handler.CustomFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomFailHandler customFailHandler;

    @Bean //로그인 유지 기능 rememberMe 나중에 추가하기
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable) //나중에 지워주면 좋음  Cross-Site Request Forgery (CSRF) 보호 기능을 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**","/login", "/forgot-password")
                .permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
//                .loginProcessingUrl("/loginProc")
                .usernameParameter("empId") //사원 번호를 통해서 정보 조회
                .defaultSuccessUrl("/", true) //로그인 성공 시 메인 페이지로
                .failureHandler(customFailHandler) //페이지 자체에 로그인 실패 메시지 표시를 위해 커스텀
                .permitAll())
            .logout(LogoutConfigurer::permitAll);

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return w -> w.ignoring().requestMatchers(
            "/images/**", "/css/**", "/js/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * 로그인에서 사번이나 비밀번호가 일치하지 않으면 페이지에 에러 메시지를 띄우기 위한 핸들러
     * @return
     */


}