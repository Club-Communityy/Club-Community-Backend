package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubApplicationForm;
import com.example.ClubCommunity.Club.dto.ClubApplicationFormDto;
import com.example.ClubCommunity.Club.repository.ClubApplicationFormRepository;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ClubApplicationFormService {

    private final ClubApplicationFormRepository clubApplicationFormRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ClubApplicationFormDto uploadApplicationForm(Long clubId, Long memberId, MultipartFile file) throws IOException {
        // 신청서 파일 업로드 처리
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + clubId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 회원을 찾을 수 없습니다: " + memberId));

        ClubApplicationForm form = ClubApplicationForm.builder()
                .club(club)
                .member(member)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .build();

        clubApplicationFormRepository.save(form);
        return toDto(form);
    }

    @Transactional(readOnly = true)
    public ClubApplicationFormDto downloadApplicationForm(Long formId) {
        // 신청서 파일 다운로드 처리
        ClubApplicationForm form = clubApplicationFormRepository.findById(formId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 신청서를 찾을 수 없습니다: " + formId));
        return toDto(form);
    }

    private ClubApplicationFormDto toDto(ClubApplicationForm entity) {
        // 엔티티를 DTO로 변환
        return ClubApplicationFormDto.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .memberId(entity.getMember().getId())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .data(entity.getData())
                .build();
    }
}
