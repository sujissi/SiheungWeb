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
                        // 1. 누구나 접속 가능
                        .requestMatchers("/", "/complaints/**", "/css/**", "/images/**", "/uploads/**", "/members/**", "/h2-console/**").permitAll()

                        // 2. [추가] /admin/ 으로 시작하는 주소는 'ADMIN' 권한만 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 3. 나머지는 로그인 필수
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