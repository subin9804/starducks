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
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
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
//            .csrf(AbstractHttpConfigurer::disable) // ajax 사용하려면 토큰 필요. 우선은 disable 해둠.  Cross-Site Request Forgery (CSRF) 보호 기능을 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**","/login", "/forgotPwd") //로그인 안해도 접속 가능한 주소 /**는 모든 하위 주소에 접근 가능하게 만듦. 나중에 지울 예정
                .permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login") //로그인 하는 페이지
                .usernameParameter("empId") //사원 번호를 통해서 정보 조회
                .defaultSuccessUrl("/", true) //로그인 성공 시 메인 페이지로
                .failureHandler(customFailHandler) //페이지 자체에 로그인 실패 메시지 표시를 위해 커스텀핸들러 따로 제작함
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