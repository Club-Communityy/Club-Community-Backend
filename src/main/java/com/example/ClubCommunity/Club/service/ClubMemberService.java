package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubMember;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Club.dto.ClubMemberDto;
import com.example.ClubCommunity.Club.repository.ClubMemberRepository;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.Member.repository.MemberRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ClubMemberDto applyForMembership(Long clubId, Long memberId) {
        // 동아리 가입 신청 처리
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + clubId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 회원을 찾을 수 없습니다: " + memberId));

        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(member)
                .status(ClubMember.MembershipStatus.APPLIED)
                .build();

        clubMemberRepository.save(clubMember);
        return toDto(clubMember);
    }

    @Transactional(readOnly = true)
    public List<ClubMemberDto> getClubMembershipApplications(Long clubId) {
        // 동아리 가입 신청 목록 조회
        return clubMemberRepository.findAll().stream()
                .filter(cm -> cm.getClub().getId().equals(clubId))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClubMemberDto approveMembershipApplication(Long id) {
        // 동아리 가입 신청 승인 처리
        ClubMember clubMember = clubMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 회원을 찾을 수 없습니다: " + id));
        clubMember.setStatus(ClubMember.MembershipStatus.APPROVED);
        clubMemberRepository.save(clubMember);
        return toDto(clubMember);
    }

    @Transactional
    public ClubMemberDto rejectMembershipApplication(Long id) {
        // 동아리 가입 신청 거절 처리
        ClubMember clubMember = clubMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 회원을 찾을 수 없습니다: " + id));
        clubMember.setStatus(ClubMember.MembershipStatus.REJECTED);
        clubMemberRepository.save(clubMember);
        return toDto(clubMember);
    }

    @Transactional
    public List<ClubMemberDto> approveMembershipApplications(List<Long> ids) {
        // 다건 동아리 가입 신청 승인 처리
        List<ClubMember> clubMembers = clubMemberRepository.findAllById(ids).stream()
                .peek(clubMember -> clubMember.setStatus(ClubMember.MembershipStatus.APPROVED))
                .collect(Collectors.toList());
        clubMemberRepository.saveAll(clubMembers);
        return clubMembers.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ClubMemberDto> rejectMembershipApplications(List<Long> ids) {
        // 다건 동아리 가입 신청 거절 처리
        List<ClubMember> clubMembers = clubMemberRepository.findAllById(ids).stream()
                .peek(clubMember -> clubMember.setStatus(ClubMember.MembershipStatus.REJECTED))
                .collect(Collectors.toList());
        clubMemberRepository.saveAll(clubMembers);
        return clubMembers.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ClubMemberDto withdrawMembership(Long id) {
        // 동아리 탈퇴 처리
        ClubMember clubMember = clubMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 회원을 찾을 수 없습니다: " + id));
        clubMember.setStatus(ClubMember.MembershipStatus.WITHDRAWN);
        clubMemberRepository.save(clubMember);
        return toDto(clubMember);
    }

    private ClubMemberDto toDto(ClubMember entity) {
        // 엔티티를 DTO로 변환
        return ClubMemberDto.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .memberId(entity.getMember().getId())
                .status(entity.getStatus())
                .build();
    }
}
