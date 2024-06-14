package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubDetails;
import com.example.ClubCommunity.Club.dto.ClubDetailsDto;
import com.example.ClubCommunity.Club.repository.ClubDetailsRepository;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubDetailsService {

    private final ClubDetailsRepository clubDetailsRepository;
    private final ClubRepository clubRepository;

    @Transactional
    public ClubDetailsDto createOrUpdateClubDetails(ClubDetailsDto detailsDto) {
        Club club = clubRepository.findById(detailsDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + detailsDto.getClubId()));

        ClubDetails details = clubDetailsRepository.findByClub(club)
                .orElse(new ClubDetails());

        details.setClub(club);
        details.setIntroduction(detailsDto.getIntroduction());
        details.setMainImage(detailsDto.getMainImage());
        details.setRegularMeetingTime(detailsDto.getRegularMeetingTime());
        details.setPresidentName(detailsDto.getPresidentName());
        details.setVicePresidentName(detailsDto.getVicePresidentName());
        details.setTreasurerName(detailsDto.getTreasurerName());

        clubDetailsRepository.save(details);
        return toDto(details);
    }

    @Transactional(readOnly = true)
    public ClubDetailsDto getClubDetails(Long clubId) {
        ClubDetails details = clubDetailsRepository.findByClubId(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 정보를 찾을 수 없습니다: " + clubId));
        return toDto(details);
    }

    private ClubDetailsDto toDto(ClubDetails entity) {
        return ClubDetailsDto.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .introduction(entity.getIntroduction())
                .mainImage(entity.getMainImage())
                .regularMeetingTime(entity.getRegularMeetingTime())
                .presidentName(entity.getPresidentName())
                .vicePresidentName(entity.getVicePresidentName())
                .treasurerName(entity.getTreasurerName())
                .applicationFormUrl("http://localhost:8080/api/club-application-forms/download/1")
                .build();
    }
}
