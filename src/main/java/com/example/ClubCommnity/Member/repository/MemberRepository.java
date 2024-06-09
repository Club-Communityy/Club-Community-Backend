package com.example.ClubCommnity.Member.repository;

import com.example.ClubCommnity.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByStudentId(String email);
    Optional<Member> findByPhoneNumber(String email);

}
