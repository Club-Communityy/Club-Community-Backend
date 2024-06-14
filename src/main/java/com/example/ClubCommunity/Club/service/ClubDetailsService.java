package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubDetails;
import com.example.ClubCommunity.Club.domain.ClubApplicationForm;
import com.example.ClubCommunity.Club.dto.ClubDetailsDto;
import com.example.ClubCommunity.Club.repository.ClubDetailsRepository;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.Club.repository.ClubApplicationFormRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubDetailsService {

    private final ClubDetailsRepository clubDetailsRepository;
    private final ClubRepository clubRepository;
    private final ClubApplicationFormRepository clubApplicationFormRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ClubDetailsDto createOrUpdateClubDetails(ClubDetailsDto detailsDto, MultipartFile applicationForm, Long memberId) {
        Club club = clubRepository.findById(detailsDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + detailsDto.getClubId()));

        List<ClubDetails> detailsList = clubDetailsRepository.findAllByClubId(detailsDto.getClubId());
        ClubDetails details;
        if (detailsList.size() == 1) {
            details = detailsList.get(0);
        } else if (detailsList.isEmpty()) {
            details = new ClubDetails();
            details.setClub(club);
        } else {
            throw new RuntimeException("여러 개의 클럽 상세 정보가 발견되었습니다.");
        }

        details.setIntroduction(detailsDto.getIntroduction());
        details.setMainImage(detailsDto.getMainImage());
        details.setRegularMeetingTime(detailsDto.getRegularMeetingTime());
        details.setPresidentName(detailsDto.getPresidentName());
        details.setVicePresidentName(detailsDto.getVicePresidentName());
        details.setTreasurerName(detailsDto.getTreasurerName());

        clubDetailsRepository.save(details);

        if (applicationForm != null && !applicationForm.isEmpty()) {
            try {
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 회원을 찾을 수 없습니다: " + memberId));

                // 기존 applicationForm 삭제
                List<ClubApplicationForm> existingForms = clubApplicationFormRepository.findAllByClubId(detailsDto.getClubId());
                for (ClubApplicationForm existingForm : existingForms) {
                    clubApplicationFormRepository.delete(existingForm);
                }

                // 새로운 applicationForm 저장
                ClubApplicationForm form = ClubApplicationForm.builder()
                        .club(club)
                        .member(member)
                        .fileName(applicationForm.getOriginalFilename())
                        .fileType(applicationForm.getContentType())
                        .data(applicationForm.getBytes())
                        .build();
                clubApplicationFormRepository.save(form);
            } catch (IOException e) {
                throw new RuntimeException("신청서를 저장하는 데 실패했습니다.", e);
            }
        }

        return toDto(details);
    }


    @Transactional(readOnly = true)
    public boolean clubDetailsExists(Long clubId) {
        return clubDetailsRepository.findByClubId(clubId).isPresent();
    }

    @Transactional(readOnly = true)
    public ClubDetailsDto getClubDetails(Long clubId) {
        ClubDetails details = clubDetailsRepository.findByClubId(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 정보를 찾을 수 없습니다: " + clubId));
        return toDto(details);
    }

    @Transactional(readOnly = true)
    public byte[] downloadApplicationForm(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + clubId));
        ClubApplicationForm form = clubApplicationFormRepository.findByClub(club)
                .orElseThrow(() -> new ResourceNotFoundException("해당 동아리에 대한 신청서를 찾을 수 없습니다: " + clubId));
        return form.getData();
    }

    private ClubDetailsDto toDto(ClubDetails entity) {
        ClubDetailsDto detailsDto = ClubDetailsDto.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .introduction(entity.getIntroduction())
                .mainImage(entity.getMainImage())
                .regularMeetingTime(entity.getRegularMeetingTime())
                .presidentName(entity.getPresidentName())
                .vicePresidentName(entity.getVicePresidentName())
                .treasurerName(entity.getTreasurerName())
                .build();

        Optional<ClubApplicationForm> applicationForm = clubApplicationFormRepository.findByClub(entity.getClub());
        applicationForm.ifPresent(form -> detailsDto.setApplicationFormUrl("/api/club-application-forms/download/" + form.getId()));

        return detailsDto;
    }

}
