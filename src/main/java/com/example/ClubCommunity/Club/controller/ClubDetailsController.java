package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubDetailsDto;
import com.example.ClubCommunity.Club.service.ClubDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/club-details")
@RequiredArgsConstructor
public class ClubDetailsController {

    private final ClubDetailsService clubDetailsService;

    @PostMapping
    public ResponseEntity<ClubDetailsDto> createOrUpdateClubDetails(@RequestBody ClubDetailsDto detailsDto) {
        // 동아리 기본 정보 입력/수정 처리
        ClubDetailsDto updatedDetails = clubDetailsService.createOrUpdateClubDetails(detailsDto);
        return ResponseEntity.ok(updatedDetails);
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDetailsDto> getClubDetails(@PathVariable("clubId") Long clubId) {
        // 동아리 기본 정보 조회
        ClubDetailsDto details = clubDetailsService.getClubDetails(clubId);
        return ResponseEntity.ok(details);
    }
}
