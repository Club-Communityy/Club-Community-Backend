package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubDetailsDto;
import com.example.ClubCommunity.Club.service.ClubDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/club-details")
@RequiredArgsConstructor
public class ClubDetailsController {

    private final ClubDetailsService clubDetailsService;

    @PostMapping
    public ResponseEntity<ClubDetailsDto> createClubDetails(
            @RequestParam("clubId") Long clubId,
            @RequestParam(value = "introduction", required = false) String introduction,
            @RequestParam(value = "history", required = false) String history,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "regularMeetingTime", required = false) String regularMeetingTime,
            @RequestParam(value = "presidentName", required = false) String presidentName,
            @RequestParam(value = "vicePresidentName", required = false) String vicePresidentName,
            @RequestParam(value = "treasurerName", required = false) String treasurerName) {

        ClubDetailsDto detailsDto = new ClubDetailsDto();
        detailsDto.setClubId(clubId);
        detailsDto.setIntroduction(introduction);
        detailsDto.setHistory(history);
        detailsDto.setRegularMeetingTime(regularMeetingTime);
        detailsDto.setPresidentName(presidentName);
        detailsDto.setVicePresidentName(vicePresidentName);
        detailsDto.setTreasurerName(treasurerName);

        if (mainImage != null && !mainImage.isEmpty()) {
            try {
                detailsDto.setMainImage(mainImage.getBytes());
            } catch (IOException e) {
                // 예외 처리
            }
        }

        ClubDetailsDto updatedDetails = clubDetailsService.createOrUpdateClubDetails(detailsDto);
        return ResponseEntity.ok(updatedDetails);
    }

    @PutMapping
    public ResponseEntity<ClubDetailsDto> updateClubDetails(
            @RequestParam("clubId") Long clubId,
            @RequestParam(value = "introduction", required = false) String introduction,
            @RequestParam(value = "history", required = false) String history,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "regularMeetingTime", required = false) String regularMeetingTime,
            @RequestParam(value = "presidentName", required = false) String presidentName,
            @RequestParam(value = "vicePresidentName", required = false) String vicePresidentName,
            @RequestParam(value = "treasurerName", required = false) String treasurerName) {

        ClubDetailsDto detailsDto = new ClubDetailsDto();
        detailsDto.setClubId(clubId);
        detailsDto.setIntroduction(introduction);
        detailsDto.setHistory(history);
        detailsDto.setRegularMeetingTime(regularMeetingTime);
        detailsDto.setPresidentName(presidentName);
        detailsDto.setVicePresidentName(vicePresidentName);
        detailsDto.setTreasurerName(treasurerName);

        if (mainImage != null && !mainImage.isEmpty()) {
            try {
                detailsDto.setMainImage(mainImage.getBytes());
            } catch (IOException e) {
                // 예외 처리
            }
        }

        ClubDetailsDto updatedDetails = clubDetailsService.createOrUpdateClubDetails(detailsDto);
        return ResponseEntity.ok(updatedDetails);
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDetailsDto> getClubDetails(@PathVariable("clubId") Long clubId) {
        ClubDetailsDto details = clubDetailsService.getClubDetails(clubId);
        return ResponseEntity.ok(details);
    }
}
