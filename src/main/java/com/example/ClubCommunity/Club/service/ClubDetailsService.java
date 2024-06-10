package com.example.ClubCommunity.Club.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubDetails;
import com.example.ClubCommunity.Club.dto.ClubDetailsDto;
import com.example.ClubCommunity.Club.repository.ClubDetailsRepository;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import com.example.ClubCommunity.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubDetailsService {

    private final ClubDetailsRepository clubDetailsRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ClubDetailsDto createOrUpdateClubDetails(ClubDetailsDto detailsDto) {
        Club club = clubRepository.findById(detailsDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + detailsDto.getClubId()));

        ClubDetails details = clubDetailsRepository.findByClub(club)
                .orElse(new ClubDetails());

        details.setClub(club);
        details.setIntroduction(detailsDto.getIntroduction());
        details.setHistory(detailsDto.getHistory());
        details.setMainImage(detailsDto.getMainImage());
        details.setRegularMeetingTime(detailsDto.getRegularMeetingTime());

        if (detailsDto.getPresidentName() != null) {
            Member president = memberRepository.findByUsername(detailsDto.getPresidentName())
                    .orElseThrow(() -> new ResourceNotFoundException("해당 이름으로 회원을 찾을 수 없습니다: " + detailsDto.getPresidentName()));
            details.setPresident(president);
        }

        if (detailsDto.getVicePresidentName() != null) {
            Member vicePresident = memberRepository.findByUsername(detailsDto.getVicePresidentName())
                    .orElseThrow(() -> new ResourceNotFoundException("해당 이름으로 회원을 찾을 수 없습니다: " + detailsDto.getVicePresidentName()));
            details.setVicePresident(vicePresident);
        }

        if (detailsDto.getTreasurerName() != null) {
            Member treasurer = memberRepository.findByUsername(detailsDto.getTreasurerName())
                    .orElseThrow(() -> new ResourceNotFoundException("해당 이름으로 회원을 찾을 수 없습니다: " + detailsDto.getTreasurerName()));
            details.setTreasurer(treasurer);
        }

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
                .history(entity.getHistory())
                .mainImage(entity.getMainImage())
                .regularMeetingTime(entity.getRegularMeetingTime())
                .presidentName(entity.getPresident() != null ? entity.getPresident().getUsername() : null)
                .vicePresidentName(entity.getVicePresident() != null ? entity.getVicePresident().getUsername() : null)
                .treasurerName(entity.getTreasurer() != null ? entity.getTreasurer().getUsername() : null)
                .build();
    }
}
