package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.MembershipApplication;
import com.example.ClubCommunity.Club.dto.MembershipApplicationDto;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.Club.repository.MembershipApplicationRepository;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.repository.MemberRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipApplicationService {

    private final MembershipApplicationRepository membershipApplicationRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MembershipApplicationDto uploadApplicationForm(Long clubId, Long memberId, MultipartFile file) throws IOException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + clubId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 회원을 찾을 수 없습니다: " + memberId));

        MembershipApplication form = MembershipApplication.builder()
                .club(club)
                .member(member)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .build();

        membershipApplicationRepository.save(form);
        return toDto(form);
    }

    @Transactional(readOnly = true)
    public MembershipApplicationDto downloadApplicationForm(Long formId) {
        MembershipApplication form = membershipApplicationRepository.findById(formId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 신청서를 찾을 수 없습니다: " + formId));
        return toDto(form);
    }

    @Transactional(readOnly = true)
    public List<MembershipApplicationDto> getClubApplications(Long clubId) {
        return membershipApplicationRepository.findByClubId(clubId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MembershipApplicationDto toDto(MembershipApplication entity) {
        return MembershipApplicationDto.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .memberId(entity.getMember().getId())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .data(entity.getData())
                .build();
    }
}
