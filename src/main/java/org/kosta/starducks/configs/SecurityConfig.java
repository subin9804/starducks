package org.kosta.starducks.configs;

import org.kosta.starducks.auth.handler.CustomFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; //csrf disable 할 때 필요
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomFailHandler customFailHandler;

    @Bean //시큐리티 옵션을 커스텀할 수 있는 곳
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable) // csrf 시큐리티 활성화를 위해 나중에 없애야하는 줄. ajax 사용하려면 <head>, script에  토큰 필요. form 형식은 th:action을 사용하면 자동으로 토큰 추가됨
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**","/login", "/forgotPwd") //로그인 안해도 접속 가능한 주소 /**는 모든 하위 주소에 접근 가능하게 만듦. 나중에 지울 예정
                .permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login") //로그인 하는 페이지
                .defaultSuccessUrl("/", true) //로그인 성공 시 메인 페이지로
                .failureHandler(customFailHandler) //페이지 자체에 로그인 실패 메시지 표시를 위해 커스텀핸들러 따로 제작함
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 리디렉트할 URL 지정
                .permitAll());

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