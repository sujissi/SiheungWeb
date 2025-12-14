package com.signal.web.service;

import com.signal.web.domain.Member;
import com.signal.web.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member create(String username, String email, String password, String nickname) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));

        return memberRepository.save(member);
    }
}