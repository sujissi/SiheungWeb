package com.signal.web.config;

import com.signal.web.service.AuthTokenService;
import com.signal.web.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenService authTokenService;
    private final MemberService memberService;

    public LoginSuccessHandler(AuthTokenService authTokenService, MemberService memberService) {
        this.authTokenService = authTokenService;
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String token = authTokenService.generateToken(username);
        memberService.updateLoginSuccess(username, token);

        Cookie cookie = new Cookie(AuthTokenService.COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);

        response.sendRedirect("/complaints/list");
    }
}
