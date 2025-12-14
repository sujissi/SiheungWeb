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
        // 1. DB에서 회원 찾기
        Optional<Member> _member = memberRepository.findByUsername(username);

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 2. 스프링 시큐리티가 이해할 수 있는 User 객체로 변환해서 리턴
        Member member = _member.get();
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}