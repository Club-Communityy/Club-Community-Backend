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
        // 동아리 기본 정보 입력/수정 처리
        Club club = clubRepository.findById(detailsDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + detailsDto.getClubId()));
        ClubDetails details = toEntity(detailsDto, club);
        clubDetailsRepository.save(details);
        return toDto(details);
    }

    @Transactional(readOnly = true)
    public ClubDetailsDto getClubDetails(Long clubId) {
        // 동아리 기본 정보 조회
        ClubDetails details = clubDetailsRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리 정보를 찾을 수 없습니다: " + clubId));
        return toDto(details);
    }

    private ClubDetails toEntity(ClubDetailsDto dto, Club club) {
        // DTO를 엔티티로 변환
        return ClubDetails.builder()
                .club(club)
                .introduction(dto.getIntroduction())
                .history(dto.getHistory())
                .mainImage(dto.getMainImage())
                .regularMeetingTime(dto.getRegularMeetingTime())
                .officers(dto.getOfficers())
                .build();
    }

    private ClubDetailsDto toDto(ClubDetails entity) {
        // 엔티티를 DTO로 변환
        return ClubDetailsDto.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .introduction(entity.getIntroduction())
                .history(entity.getHistory())
                .mainImage(entity.getMainImage())
                .regularMeetingTime(entity.getRegularMeetingTime())
                .officers(entity.getOfficers())
                .build();
    }
}
