package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubMemberDto;
import com.example.ClubCommunity.Club.service.ClubMemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club-members")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping("/apply")
    public ResponseEntity<ClubMemberDto> applyForMembership(@RequestParam("clubId") Long clubId, @RequestParam("memberId") Long memberId) {
        // 동아리 가입 신청 처리
        ClubMemberDto createdApplication = clubMemberService.applyForMembership(clubId, memberId);
        return ResponseEntity.ok(createdApplication);
    }

    @GetMapping("/members/{clubId}")
    public ResponseEntity<List<ClubMemberDto>> getAllClubMembers(@PathVariable("clubId") Long clubId) {
        // 동아리의 모든 회원 조회
        List<ClubMemberDto> members = clubMemberService.getAllClubMembers(clubId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/applications/{clubId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLUBMANAGER')")
    public ResponseEntity<List<ClubMemberDto>> getClubMembershipApplications(@PathVariable("clubId") Long clubId) {
        // 동아리 가입 신청 목록 조회 (신청 대기 상태)
        List<ClubMemberDto> applications = clubMemberService.getClubMembershipApplications(clubId);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLUBMANAGER')")
    public ResponseEntity<ClubMemberDto> approveMembershipApplication(@PathVariable("id") Long id) {
        // 동아리 가입 신청 승인 처리
        ClubMemberDto approvedApplication = clubMemberService.approveMembershipApplication(id);
        return ResponseEntity.ok(approvedApplication);
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLUBMANAGER')")
    public ResponseEntity<ClubMemberDto> rejectMembershipApplication(@PathVariable("id") Long id) {
        // 동아리 가입 신청 거절 처리
        ClubMemberDto rejectedApplication = clubMemberService.rejectMembershipApplication(id);
        return ResponseEntity.ok(rejectedApplication);
    }

    @PutMapping("/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLUBMANAGER')")
    public ResponseEntity<List<ClubMemberDto>> approveMembershipApplications(@RequestBody IdsRequest idsRequest) {
        // 다건 동아리 가입 신청 승인 처리
        List<ClubMemberDto> approvedApplications = clubMemberService.approveMembershipApplications(idsRequest.getIds());
        return ResponseEntity.ok(approvedApplications);
    }

    @PutMapping("/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLUBMANAGER')")
    public ResponseEntity<List<ClubMemberDto>> rejectMembershipApplications(@RequestBody IdsRequest idsRequest) {
        // 다건 동아리 가입 신청 거절 처리
        List<ClubMemberDto> rejectedApplications = clubMemberService.rejectMembershipApplications(idsRequest.getIds());
        return ResponseEntity.ok(rejectedApplications);
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<ClubMemberDto> withdrawMembership(@PathVariable("id") Long id) {
        // 동아리 탈퇴 처리 (자신의 탈퇴)
        ClubMemberDto withdrawnApplication = clubMemberService.withdrawMembership(id);
        return ResponseEntity.ok(withdrawnApplication);
    }

    @PutMapping("/withdraw")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLUBMANAGER')")
    public ResponseEntity<ClubMemberDto> withdrawMembershipByAdmin(@RequestParam("memberId") Long memberId) {
        // 동아리 탈퇴 처리 (관리자에 의해)
        ClubMemberDto withdrawnApplication = clubMemberService.withdrawMembership(memberId);
        return ResponseEntity.ok(withdrawnApplication);
    }

    @Data
    public static class IdsRequest {
        private List<Long> ids;
    }
}
