package com.example.ClubCommunity.Club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubDetailsDto {
    private Long id; // 동아리 상세정보 ID
    private Long clubId; // 동아리 ID
    private String introduction; // 동아리 소개
    private byte[] mainImage; // 대표 사진 데이터
    private String regularMeetingTime; // 정기 모임 시간
    private String presidentName; // 회장 이름
    private String vicePresidentName; // 부회장 이름
    private String treasurerName; // 총무 이름
    private String applicationFormUrl; // 동아리 가입 신청서 다운로드 URL
}
