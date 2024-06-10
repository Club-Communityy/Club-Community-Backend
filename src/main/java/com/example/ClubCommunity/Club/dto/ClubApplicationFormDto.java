package com.example.ClubCommunity.Club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubApplicationFormDto {
    private Long id; // 신청서 ID
    private Long clubId; // 동아리 ID
    private Long memberId; // 회원 ID
    private String fileName; // 파일 이름
    private String fileType; // 파일 타입
    private byte[] data; // 파일 데이터
}
