package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubMemberDto;
import com.example.ClubCommunity.Club.service.ClubMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club-members")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping("/apply")
    public ResponseEntity<ClubMemberDto> applyForMembership(@RequestParam Long clubId, @RequestParam Long memberId) {
        // 동아리 가입 신청 처리
        ClubMemberDto createdApplication = clubMemberService.applyForMembership(clubId, memberId);
        return ResponseEntity.ok(createdApplication);
    }

    @GetMapping("/applications/{clubId}")
    public ResponseEntity<List<ClubMemberDto>> getClubMembershipApplications(@PathVariable("clubId") Long clubId) {
        // 동아리 가입 신청 목록 조회
        List<ClubMemberDto> applications = clubMemberService.getClubMembershipApplications(clubId);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ClubMemberDto> approveMembershipApplication(@PathVariable("id") Long id) {
        // 동아리 가입 신청 승인 처리
        ClubMemberDto approvedApplication = clubMemberService.approveMembershipApplication(id);
        return ResponseEntity.ok(approvedApplication);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ClubMemberDto> rejectMembershipApplication(@PathVariable("id") Long id) {
        // 동아리 가입 신청 거절 처리
        ClubMemberDto rejectedApplication = clubMemberService.rejectMembershipApplication(id);
        return ResponseEntity.ok(rejectedApplication);
    }

    @PutMapping("/approve")
    public ResponseEntity<List<ClubMemberDto>> approveMembershipApplications(@RequestBody List<Long> ids) {
        // 다건 동아리 가입 신청 승인 처리
        List<ClubMemberDto> approvedApplications = clubMemberService.approveMembershipApplications(ids);
        return ResponseEntity.ok(approvedApplications);
    }

    @PutMapping("/reject")
    public ResponseEntity<List<ClubMemberDto>> rejectMembershipApplications(@RequestBody List<Long> ids) {
        // 다건 동아리 가입 신청 거절 처리
        List<ClubMemberDto> rejectedApplications = clubMemberService.rejectMembershipApplications(ids);
        return ResponseEntity.ok(rejectedApplications);
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<ClubMemberDto> withdrawMembership(@PathVariable("id") Long id) {
        // 동아리 탈퇴 처리
        ClubMemberDto withdrawnApplication = clubMemberService.withdrawMembership(id);
        return ResponseEntity.ok(withdrawnApplication);
    }
}
