package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.controller.ClubController.RejectionRequest;
import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.dto.ClubDto;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.service.MemberService;
import com.example.ClubCommunity.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional
    public ClubDto applyForClub(ClubDto clubDto, Authentication authentication) {
        // 동아리 신청 처리
        Member applicant = getAuthenticatedMember(authentication); // 인증된 사용자 정보 가져오기
        Club club = toEntity(clubDto, applicant);
        club.setStatus(Club.ClubStatus.PENDING);
        clubRepository.save(club);
        return toDto(club);
    }

    @Transactional(readOnly = true)
    public List<ClubDto> getApprovedClubApplications() {
        // 승인된 동아리 신청 목록 조회
        return clubRepository.findByStatus(Club.ClubStatus.APPROVED).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClubDto> getPendingClubApplications() {
        // PENDING 상태의 동아리 신청 목록 조회
        return clubRepository.findByStatus(Club.ClubStatus.PENDING).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ClubDto approveClubApplication(Long id) {
        // 동아리 신청 승인 처리
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + id));
        if (club.getStatus() != Club.ClubStatus.PENDING) {
            throw new IllegalStateException("동아리 신청이 PENDING 상태가 아닙니다.");
        }
        club.setStatus(Club.ClubStatus.APPROVED);
        memberService.changeUserRole(club.getApplicant().getId(), Member.UserType.ROLE_CLUBMANAGER);
        clubRepository.save(club);
        return toDto(club);
    }

    @Transactional
    public ClubDto rejectClubApplication(Long id, String reason) {
        // 동아리 신청 거절 처리
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + id));
        if (club.getStatus() != Club.ClubStatus.PENDING) {
            throw new IllegalStateException("동아리 신청이 PENDING 상태가 아닙니다.");
        }
        club.setStatus(Club.ClubStatus.REJECTED);
        club.setRejectionReason(reason);
        clubRepository.save(club);
        return toDto(club);
    }

    @Transactional
    public List<ClubDto> approveClubApplications(List<Long> ids) {
        // 다건 동아리 신청 승인 처리
        List<Club> clubs = clubRepository.findAllById(ids).stream()
                .peek(club -> {
                    if (club.getStatus() == Club.ClubStatus.PENDING) {
                        club.setStatus(Club.ClubStatus.APPROVED);
                        memberService.changeUserRole(club.getApplicant().getId(), Member.UserType.ROLE_CLUBMANAGER);
                    } else {
                        throw new IllegalStateException("동아리 신청이 PENDING 상태가 아닙니다: " + club.getId());
                    }
                }).collect(Collectors.toList());
        clubRepository.saveAll(clubs);
        return clubs.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ClubDto> rejectClubApplications(List<RejectionRequest> rejections) {
        // 다건 동아리 신청 거절 처리
        Map<Long, String> rejectionMap = rejections.stream()
                .collect(Collectors.toMap(RejectionRequest::getId, RejectionRequest::getReason));

        List<Club> clubs = clubRepository.findAllById(rejectionMap.keySet()).stream()
                .peek(club -> {
                    if (club.getStatus() == Club.ClubStatus.PENDING) {
                        club.setStatus(Club.ClubStatus.REJECTED);
                        club.setRejectionReason(rejectionMap.get(club.getId()));
                    } else {
                        throw new IllegalStateException("동아리 신청이 PENDING 상태가 아닙니다: " + club.getId());
                    }
                }).collect(Collectors.toList());
        clubRepository.saveAll(clubs);
        return clubs.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Club toEntity(ClubDto dto, Member applicant) {
        // DTO를 엔티티로 변환
        return Club.builder()
                .name(dto.getName())
                .type(dto.getType())
                .applicant(applicant)
                .advisorName(dto.getAdvisorName())
                .advisorMajor(dto.getAdvisorMajor())
                .advisorContact(dto.getAdvisorContact())
                .status(dto.getStatus())
                .rejectionReason(dto.getRejectionReason())
                .build();
    }

    private ClubDto toDto(Club entity) {
        // 엔티티를 DTO로 변환
        Member applicant = entity.getApplicant();
        return ClubDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .applicantName(applicant.getUsername())
                .applicantDepartment(applicant.getDepartment())
                .applicantStudentId(applicant.getStudentId())
                .applicantContact(applicant.getPhoneNumber())
                .advisorName(entity.getAdvisorName())
                .advisorMajor(entity.getAdvisorMajor())
                .advisorContact(entity.getAdvisorContact())
                .status(entity.getStatus())
                .rejectionReason(entity.getRejectionReason())
                .build();
    }

    private Member getAuthenticatedMember(Authentication authentication) {
        // 인증된 사용자 정보 가져오기
        String loginId = authentication.getName();
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 로그인 ID로 회원을 찾을 수 없습니다: " + loginId));
    }
}
