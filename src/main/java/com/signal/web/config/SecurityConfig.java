package com.signal.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter; // [추가]
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 페이지 권한 설정
                .authorizeHttpRequests((authorize) -> authorize
                        // h2-console 주소도 로그인 없이 접속 허용 목록에 추가해야 함 (/h2-console/**)
                        .requestMatchers("/", "/complaints/**", "/css/**", "/images/**", "/uploads/**", "/members/signup", "/members/login", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )

                // ▼▼▼ [추가 1] H2 콘솔 사용을 위한 CSRF 무시 설정 ▼▼▼
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))

                // ▼▼▼ [추가 2] H2 콘솔 화면 깨짐 방지 (프레임 허용) ▼▼▼
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))

                // 2. 로그인 설정
                .formLogin((form) -> form
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/complaints/list")
                        .permitAll()
                )
                // 3. 로그아웃 설정
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/complaints/list")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}