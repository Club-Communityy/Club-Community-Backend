package com.example.ClubCommnity.Member.service;

import com.example.ClubCommnity.Member.domain.Member;
import com.example.ClubCommnity.Member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member;
        if (username.contains("@")) {
            member = memberRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + username));
        } else {
            member = memberRepository.findByLoginId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("해당 로그인 ID를 가진 사용자를 찾을 수 없습니다: " + username));
        }

        String loginId = member.getLoginId() != null ? member.getLoginId() : "";
        String passwordHash = member.getPasswordHash() != null ? member.getPasswordHash() : "";

        return User
                .withUsername(username)
                .password(passwordHash)
                .authorities(member.getUserType().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
