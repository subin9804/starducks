package org.kosta.starducks.configs;

import org.kosta.starducks.auth.handler.CustomAccessDeniedHandler;
import org.kosta.starducks.auth.handler.CustomFailHandler;
import org.kosta.starducks.auth.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomFailHandler customFailHandler;

    @Bean
    public SimpleUrlAuthenticationSuccessHandler customSuccessHandler(PasswordEncoder passwordEncoder) {
        return new CustomSuccessHandler(passwordEncoder);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean //시큐리티 옵션을 커스텀할 수 있는 곳
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
               //.csrf(AbstractHttpConfigurer::disable) // ajax 사용하려면 토큰 필요. 우선은 disable 해둠.  Cross-Site Request Forgery (CSRF) 보호 기능을 비활성화
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/forgotPwd").permitAll() // 로그인 페이지 및 비밀번호 찾기 페이지 접근 허용
//                    .requestMatchers("/**").hasAuthority("ROLE_BOSS") // 모든 경로에 대해 마스터 권한을 가진 사용자 접근 허용
                    .requestMatchers("/fina/**").hasAuthority("FINA")        // 재무부 페이지에 대한 접근 제어
                    .requestMatchers("/hr/**").hasAuthority("HR")            // 인사부 페이지에 대한 접근 제어
                    .requestMatchers("/logistic/**").hasAuthority("LOGISTIC")// 물류유통부 페이지에 대한 접근 제어
                    .requestMatchers("/general/**").hasAuthority("GENERAL")  // 총무부 페이지에 대한 접근 제어
                    .anyRequest().authenticated())
            .exceptionHandling((exceptions) -> exceptions
            .accessDeniedHandler(accessDeniedHandler()))// 그 외 모든 요청은 인증 필요
            .formLogin(form -> form
                        .loginPage("/login") //로그인 하는 페이지
                        .successHandler(customSuccessHandler(passwordEncoder()))
//                        .defaultSuccessUrl("/", true) //로그인 성공 시 메인 페이지로
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