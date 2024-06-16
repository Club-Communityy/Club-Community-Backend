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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ClubMemberDto applyForMembership(Long clubId, Long memberId, MultipartFile file, String memberName, String department, String studentId) throws IOException {
        // 동아리 가입 신청 처리
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + clubId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 회원을 찾을 수 없습니다: " + memberId));

        // 이미 신청 중인지 확인
        boolean alreadyApplied = clubMemberRepository.findAllByMemberId(memberId).stream()
                .anyMatch(cm -> cm.getClub().getId().equals(clubId) && cm.getStatus() == ClubMember.MembershipStatus.APPLIED);

        if (alreadyApplied) {
            throw new IllegalStateException("이미 해당 동아리에 가입 신청 중입니다.");
        }

        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(member)
                .status(ClubMember.MembershipStatus.APPLIED)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .memberName(memberName)
                .department(department)
                .studentId(studentId)
                .build();

        clubMemberRepository.save(clubMember);
        return toDto(clubMember);
    }

    @Transactional
    public void addClubMember(Club club, Member member) {
        // 동아리 회원 추가
        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(member)
                .status(ClubMember.MembershipStatus.APPROVED)
                .build();
        clubMemberRepository.save(clubMember);
    }

    @Transactional(readOnly = true)
    public List<ClubMemberDto> getMemberClubInfo(Long memberId) {
        // 회원이 가입한 동아리 정보 및 신청 상태 조회
        List<ClubMember> clubMembers = clubMemberRepository.findAllByMemberId(memberId);
        return clubMembers.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClubMemberDto> getPendingApplications(Long clubId) {
        // 동아리의 승인 대기 중인 회원 조회
        List<ClubMember> pendingApplications = clubMemberRepository.findAllByClubIdAndStatus(clubId, ClubMember.MembershipStatus.APPLIED);
        return pendingApplications.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ClubMemberDto approveMembershipApplication(Long id) {
        // 동아리 가입 신청 승인 처리
        ClubMember clubMember = clubMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 회원을 찾을 수 없습니다: " + id));
        if (clubMember.getStatus() == ClubMember.MembershipStatus.APPLIED) {
            clubMember.setStatus(ClubMember.MembershipStatus.APPROVED);
            clubMemberRepository.save(clubMember);
        } else {
            throw new IllegalStateException("승인할 수 없는 상태입니다.");
        }
        return toDto(clubMember);
    }

    @Transactional
    public ClubMemberDto rejectMembershipApplication(Long id) {
        // 동아리 가입 신청 거절 처리
        ClubMember clubMember = clubMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 회원을 찾을 수 없습니다: " + id));
        if (clubMember.getStatus() == ClubMember.MembershipStatus.APPLIED) {
            clubMember.setStatus(ClubMember.MembershipStatus.REJECTED);
            clubMemberRepository.save(clubMember);
        } else {
            throw new IllegalStateException("거절할 수 없는 상태입니다.");
        }
        return toDto(clubMember);
    }

    @Transactional(readOnly = true)
    public ClubMemberDto downloadApplicationForm(Long id) {
        // 동아리 가입 신청서 다운로드
        ClubMember clubMember = clubMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 회원을 찾을 수 없습니다: " + id));
        return toDto(clubMember);
    }

    @Transactional(readOnly = true)
    public List<ClubMemberDto> getAllClubMembers(Long clubId) {
        // 동아리의 모든 회원 조회
        return clubMemberRepository.findAll().stream()
                .filter(cm -> cm.getClub().getId().equals(clubId) && cm.getStatus() == ClubMember.MembershipStatus.APPROVED) //형준 수정
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClubMemberDto> getClubMembershipApplications(Long clubId) {
        // 동아리 가입 신청 목록 조회 (신청 대기 상태)
        return clubMemberRepository.findAll().stream()
                .filter(cm -> cm.getClub().getId().equals(clubId) && cm.getStatus() == ClubMember.MembershipStatus.APPLIED)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ClubMemberDto> approveMembershipApplications(List<Long> ids) {
        // 다건 동아리 가입 신청 승인 처리
        List<ClubMember> clubMembers = clubMemberRepository.findAllById(ids).stream()
                .peek(clubMember -> {
                    if (clubMember.getStatus() == ClubMember.MembershipStatus.APPLIED) {
                        clubMember.setStatus(ClubMember.MembershipStatus.APPROVED);
                    } else {
                        throw new IllegalStateException("승인할 수 없는 상태입니다.");
                    }
                })
                .collect(Collectors.toList());
        clubMemberRepository.saveAll(clubMembers);
        return clubMembers.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ClubMemberDto> rejectMembershipApplications(List<Long> ids) {
        // 다건 동아리 가입 신청 거절 처리
        List<ClubMember> clubMembers = clubMemberRepository.findAllById(ids).stream()
                .peek(clubMember -> {
                    if (clubMember.getStatus() == ClubMember.MembershipStatus.APPLIED) {
                        clubMember.setStatus(ClubMember.MembershipStatus.REJECTED);
                    } else {
                        throw new IllegalStateException("거절할 수 없는 상태입니다.");
                    }
                })
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
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .data(entity.getData())
                .memberName(entity.getMemberName())
                .department(entity.getDepartment())
                .studentId(entity.getStudentId())
                .build();
    }
}
