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

@RestController
@RequestMapping("/api/club-application-forms")
@RequiredArgsConstructor
public class ClubApplicationFormController {

    private final ClubApplicationFormService clubApplicationFormService;

    @PostMapping("/upload")
    public ResponseEntity<ClubApplicationFormDto> uploadApplicationForm(@RequestParam Long clubId,
                                                                        @RequestParam Long memberId,
                                                                        @RequestParam("file") MultipartFile file) throws IOException {
        // 신청서 파일 업로드 처리
        ClubApplicationFormDto formDto = clubApplicationFormService.uploadApplicationForm(clubId, memberId, file);
        return ResponseEntity.ok(formDto);
    }

    @GetMapping("/download/{formId}")
    public ResponseEntity<byte[]> downloadApplicationForm(@PathVariable("formId") Long formId) {
        // 신청서 파일 다운로드 처리
        ClubApplicationFormDto formDto = clubApplicationFormService.downloadApplicationForm(formId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(formDto.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + formDto.getFileName() + "\"")
                .body(formDto.getData());
    }
}
