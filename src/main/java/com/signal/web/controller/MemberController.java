package com.signal.web.controller;

import com.signal.web.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인 페이지 보여주기
    @GetMapping("/login")
    public String login() {
        return "members/login";
    }

    // 회원가입 페이지 보여주기
    @GetMapping("/signup")
    public String signup() {
        return "members/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String nickname) {

        memberService.create(username, email, password, nickname);
        return "redirect:/members/login"; // 가입 완료 후 로그인 페이지로 이동
    }
}