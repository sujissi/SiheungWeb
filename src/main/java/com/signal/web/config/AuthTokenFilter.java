package com.signal.web.config;

import com.signal.web.service.AuthTokenService;
import com.signal.web.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;
    private final MemberService memberService;

    public AuthTokenFilter(AuthTokenService authTokenService, MemberService memberService) {
        this.authTokenService = authTokenService;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = getCookie(request, AuthTokenService.COOKIE_NAME);
        if (cookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = cookie.getValue();
        String username = authTokenService.extractUsername(token);
        if (username == null || !memberService.touchIfTokenMatches(username, token)) {
            expireCookie(response);
            if (shouldRedirect(request)) {
                response.sendRedirect("/members/login");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    private void expireCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AuthTokenService.COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private boolean shouldRedirect(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/css")
                || path.startsWith("/images")
                || path.startsWith("/uploads")
                || path.startsWith("/h2-console")
                || path.startsWith("/members/login")
                || path.startsWith("/members/signup")) {
            return false;
        }
        String accept = request.getHeader("Accept");
        return accept == null || accept.contains("text/html");
    }
}
