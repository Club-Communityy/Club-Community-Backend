package com.example.ClubCommunity.Club.dto;

import com.example.ClubCommunity.Club.domain.Club.ClubType;
import com.example.ClubCommunity.Club.domain.Club.ClubStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubDto {
    private Long id; // 동아리 ID
    private String name; // 동아리 이름
    private ClubType type; // 동아리 종류 (중앙, 학과)
    private String applicantName; // 신청자 이름
    private String applicantDepartment; // 신청자 소속
    private String applicantStudentId; // 신청자 학번
    private String applicantContact; // 신청자 연락처
    private String advisorName; // 지도교수 이름
    private String advisorMajor; // 지도교수 전공
    private String advisorContact; // 지도교수 연락처
    private ClubStatus status; // 동아리 신청 상태 (검토, 승인, 거절)
    private String rejectionReason; // 거절 사유
}
