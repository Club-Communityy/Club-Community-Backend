package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.MembershipApplicationDto;
import com.example.ClubCommunity.Club.service.MembershipApplicationService;
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
import java.util.List;

@RestController
@RequestMapping("/api/membership-applications")
@RequiredArgsConstructor
public class MembershipApplicationController {

    private final MembershipApplicationService membershipApplicationService;

    @PostMapping("/upload")
    public ResponseEntity<MembershipApplicationDto> uploadApplicationForm(@RequestParam("clubId") Long clubId,
                                                                          @RequestParam("memberId") Long memberId,
                                                                          @RequestParam("file") MultipartFile file) throws IOException {
        MembershipApplicationDto formDto = membershipApplicationService.uploadApplicationForm(clubId, memberId, file);
        return ResponseEntity.ok(formDto);
    }

    @GetMapping("/download/{formId}")
    public ResponseEntity<byte[]> downloadApplicationForm(@PathVariable("formId") Long formId) throws UnsupportedEncodingException {
        MembershipApplicationDto formDto = membershipApplicationService.downloadApplicationForm(formId);

        String encodedFileName = URLEncoder.encode(formDto.getFileName(), StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(formDto.getData());
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<MembershipApplicationDto>> getClubApplications(@PathVariable("clubId") Long clubId) {
        List<MembershipApplicationDto> applications = membershipApplicationService.getClubApplications(clubId);
        return ResponseEntity.ok(applications);
    }
}
