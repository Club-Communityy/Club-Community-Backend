package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubMemberDto;
import com.example.ClubCommunity.Club.service.ClubMemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/club-members")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping("/apply")
    public ResponseEntity<ClubMemberDto> applyForMembership(@RequestParam("clubId") Long clubId,
                                                            @RequestParam("memberId") Long memberId,
                                                            @RequestParam("file") MultipartFile file,
                                                            @RequestParam("memberName") String memberName,
                                                            @RequestParam("department") String department,
                                                            @RequestParam("studentId") String studentId) throws IOException {
        // 동아리 가입 신청 처리
        ClubMemberDto createdApplication = clubMemberService.applyForMembership(clubId, memberId, file, memberName, department, studentId);
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

    @GetMapping("/member-club-info/{memberId}")
    public ResponseEntity<List<ClubMemberDto>> getMemberClubInfo(@PathVariable("memberId") Long memberId) {
        // 회원이 가입한 동아리 정보 조회
        List<ClubMemberDto> clubInfo = clubMemberService.getMemberClubInfo(memberId);
        return ResponseEntity.ok(clubInfo);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadApplicationForm(@PathVariable("id") Long id) throws UnsupportedEncodingException {
        // 동아리 가입 신청서 다운로드
        ClubMemberDto clubMember = clubMemberService.downloadApplicationForm(id);

        if (clubMember.getFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        String encodedFileName = URLEncoder.encode(clubMember.getFileName(), StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(clubMember.getData());
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
