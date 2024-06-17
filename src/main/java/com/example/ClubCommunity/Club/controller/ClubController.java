package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubDto;
import com.example.ClubCommunity.Club.service.ClubService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/apply")
    public ResponseEntity<ClubDto> applyForClub(@RequestBody ClubDto clubDto, Authentication authentication) {
        // 동아리 신청 처리
        ClubDto createdClub = clubService.applyForClub(clubDto, authentication);
        return ResponseEntity.ok(createdClub);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ClubDto>> getApprovedClubs() {
        // 승인된 동아리 목록 조회
        List<ClubDto> approvedClubs = clubService.getApprovedClubs();
        return ResponseEntity.ok(approvedClubs);
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<ClubDto>> getMyApplications(Authentication authentication) {
        // 자신이 신청한 동아리 목록 조회
        List<ClubDto> myApplications = clubService.getMyApplications(authentication);
        return ResponseEntity.ok(myApplications);
    }

    @GetMapping("/my-applications/approved")
    public ResponseEntity<List<ClubDto>> getMyApplicationsApproved(Authentication authentication) {
        // 자신이 신청한 동아리 중 승인된 목록 조회
        List<ClubDto> myApplications = clubService.getMyApplicationsApproved(authentication);
        return ResponseEntity.ok(myApplications);
    }

    @GetMapping("/applications/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClubDto>> getPendingClubApplications() {
        // PENDING 상태의 동아리 신청 목록 조회
        List<ClubDto> pendingClubApplications = clubService.getPendingClubApplications();
        return ResponseEntity.ok(pendingClubApplications);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClubDto> approveClubApplication(@PathVariable("id") Long id) {
        // 동아리 신청 승인 처리
        ClubDto approvedClub = clubService.approveClubApplication(id);
        return ResponseEntity.ok(approvedClub);
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClubDto> rejectClubApplication(@PathVariable("id") Long id, @RequestParam("reason") String reason) {
        // 동아리 신청 거절 처리
        ClubDto rejectedClub = clubService.rejectClubApplication(id, reason);
        return ResponseEntity.ok(rejectedClub);
    }

    @PutMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClubDto>> approveClubApplications(@RequestBody IdsRequest idsRequest) {
        // 다건 동아리 신청 승인 처리
        List<ClubDto> approvedClubs = clubService.approveClubApplications(idsRequest.getIds());
        return ResponseEntity.ok(approvedClubs);
    }

    @PutMapping("/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClubDto>> rejectClubApplications(@RequestBody List<RejectionRequest> rejections) {
        // 다건 동아리 신청 거절 처리
        List<ClubDto> rejectedClubs = clubService.rejectClubApplications(rejections);
        return ResponseEntity.ok(rejectedClubs);
    }

    @Data
    public static class RejectionRequest {
        private Long id;
        private String reason;
    }

    @Data
    public static class IdsRequest {
        private List<Long> ids;
    }
}
