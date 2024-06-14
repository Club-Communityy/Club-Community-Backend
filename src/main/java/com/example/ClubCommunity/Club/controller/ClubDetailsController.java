package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubDetailsDto;
import com.example.ClubCommunity.Club.service.ClubDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/club-details")
@RequiredArgsConstructor
public class ClubDetailsController {

    private final ClubDetailsService clubDetailsService;

    @PostMapping
    public ResponseEntity<ClubDetailsDto> createClubDetails(
            @RequestParam("clubId") Long clubId,
            @RequestParam(value = "introduction", required = false) String introduction,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "regularMeetingTime", required = false) String regularMeetingTime,
            @RequestParam(value = "presidentName", required = false) String presidentName,
            @RequestParam(value = "vicePresidentName", required = false) String vicePresidentName,
            @RequestParam(value = "treasurerName", required = false) String treasurerName,
            @RequestParam(value = "applicationForm", required = false) MultipartFile applicationForm,
            @RequestParam(value = "memberId", required = false) Long memberId) {

        ClubDetailsDto detailsDto = new ClubDetailsDto();
        detailsDto.setClubId(clubId);
        detailsDto.setIntroduction(introduction);
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

        ClubDetailsDto updatedDetails = clubDetailsService.createOrUpdateClubDetails(detailsDto, applicationForm, memberId);
        return ResponseEntity.ok(updatedDetails);
    }

    @PutMapping
    public ResponseEntity<ClubDetailsDto> updateClubDetails(
            @RequestParam("clubId") Long clubId,
            @RequestParam(value = "introduction", required = false) String introduction,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "regularMeetingTime", required = false) String regularMeetingTime,
            @RequestParam(value = "presidentName", required = false) String presidentName,
            @RequestParam(value = "vicePresidentName", required = false) String vicePresidentName,
            @RequestParam(value = "treasurerName", required = false) String treasurerName,
            @RequestParam(value = "applicationForm", required = false) MultipartFile applicationForm,
            @RequestParam(value = "memberId", required = false) Long memberId) {

        ClubDetailsDto detailsDto = new ClubDetailsDto();
        detailsDto.setClubId(clubId);
        detailsDto.setIntroduction(introduction);
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

        ClubDetailsDto updatedDetails = clubDetailsService.createOrUpdateClubDetails(detailsDto, applicationForm, memberId);
        return ResponseEntity.ok(updatedDetails);
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDetailsDto> getClubDetails(@PathVariable("clubId") Long clubId) {
        ClubDetailsDto details = clubDetailsService.getClubDetails(clubId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/exists/{clubId}")
    public ResponseEntity<Boolean> clubDetailsExists(@PathVariable("clubId") Long clubId) {
        boolean exists = clubDetailsService.clubDetailsExists(clubId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/download-application-form/{clubId}")
    public ResponseEntity<byte[]> downloadApplicationForm(@PathVariable("clubId") Long clubId) throws UnsupportedEncodingException {
        byte[] applicationForm = clubDetailsService.downloadApplicationForm(clubId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode("application_form.hwp", StandardCharsets.UTF_8.toString()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(applicationForm);
    }
}
