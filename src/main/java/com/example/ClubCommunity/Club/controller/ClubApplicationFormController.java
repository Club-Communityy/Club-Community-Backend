package com.example.ClubCommunity.Club.controller;

import com.example.ClubCommunity.Club.dto.ClubApplicationFormDto;
import com.example.ClubCommunity.Club.service.ClubApplicationFormService;
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
@RequestMapping("/api/club-application-forms")
@RequiredArgsConstructor
public class ClubApplicationFormController {

    private final ClubApplicationFormService clubApplicationFormService;

    @PostMapping("/upload")
    public ResponseEntity<ClubApplicationFormDto> uploadApplicationForm(@RequestParam("clubId") Long clubId,
                                                                        @RequestParam("memberId") Long memberId,
                                                                        @RequestParam("file") MultipartFile file) throws IOException {
        // 신청서 파일 업로드 처리
        ClubApplicationFormDto formDto = clubApplicationFormService.uploadApplicationForm(clubId, memberId, file);
        return ResponseEntity.ok(formDto);
    }

    @GetMapping("/download/{formId}")
    public ResponseEntity<byte[]> downloadApplicationForm(@PathVariable("formId") Long formId) throws UnsupportedEncodingException {
        // 신청서 파일 다운로드 처리
        ClubApplicationFormDto formDto = clubApplicationFormService.downloadApplicationForm(formId);

        String encodedFileName = URLEncoder.encode(formDto.getFileName(), StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(formDto.getData());
    }
}
