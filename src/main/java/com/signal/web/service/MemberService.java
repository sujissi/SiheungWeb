package com.signal.web.service;

import com.signal.web.domain.Member;
import com.signal.web.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

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
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));

        if (email != null && email.trim().isEmpty()) {
            member.setEmail(null);
        } else {
            member.setEmail(email);
        }
        return memberRepository.save(member);
    }

    public void updateLoginSuccess(String username, String loginToken) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + username));
        LocalDateTime now = LocalDateTime.now();
        member.setLoginToken(loginToken);
        member.setLastLoginAt(now);
        member.setLastSeenAt(now);
        memberRepository.save(member);
    }

    public boolean touchIfTokenMatches(String username, String loginToken) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (optionalMember.isEmpty()) {
            return false;
        }
        Member member = optionalMember.get();
        if (member.getLoginToken() == null || !member.getLoginToken().equals(loginToken)) {
            return false;
        }
        member.setLastSeenAt(LocalDateTime.now());
        memberRepository.save(member);
        return true;
    }
}
