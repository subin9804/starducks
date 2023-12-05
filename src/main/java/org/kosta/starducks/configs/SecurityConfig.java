package org.kosta.starducks.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean //로그인 유지 기능 rememberMe 나중에 추가하기
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) //나중에 지워주면 좋음  Cross-Site Request Forgery (CSRF) 보호 기능을 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**","/login", "/forgot-password")
                .permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login") //로그인 페이지 주소
                .usernameParameter("empId") //로그인 페이지에서 name="empId" 라고 지어진 사번 인풋값 인식
                .defaultSuccessUrl("/", true) //로그인 성공 시 메인 페이지로
                .failureUrl("/login/securityPassFailed") // 로그인 실패 시 리디렉션할 URL
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
}