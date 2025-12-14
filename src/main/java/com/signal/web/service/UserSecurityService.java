package com.signal.web.service;

import com.signal.web.domain.Member;
import com.signal.web.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public UserSecurityService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> _member = memberRepository.findByUsername(username);

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = _member.get();

        String role = "USER";
        if ("admin".equals(username)) {
            role = "ADMIN";
        }

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(role)
                .build();
    }
}