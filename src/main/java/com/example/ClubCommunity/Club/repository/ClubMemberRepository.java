package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findAllByMemberId(Long memberId);
    List<ClubMember> findAllByClubIdAndStatus(Long clubId, ClubMember.MembershipStatus status);
    Optional<ClubMember> findByMemberIdAndClubId(Long memberId, Long clubId);

}
