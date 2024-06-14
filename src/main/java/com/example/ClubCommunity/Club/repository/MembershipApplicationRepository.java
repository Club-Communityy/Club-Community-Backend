package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.MembershipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipApplicationRepository extends JpaRepository<MembershipApplication, Long> {
    List<MembershipApplication> findByClubId(Long clubId);
    List<MembershipApplication> findByMemberId(Long memberId);
}
