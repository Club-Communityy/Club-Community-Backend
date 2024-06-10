package com.example.ClubCommunity.Club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubDetailsDto {
    private Long id; // 동아리 상세정보 ID
    private Long clubId; // 동아리 ID
    private String introduction; // 동아리 소개
    private String history; // 동아리 역사
    private String mainImage; // 대표 사진 URL
    private String regularMeetingTime; // 정기 모임 시간
    private List<String> officers; // 임원 명단 (대표, 부대표, 총무 등)
}
