package com.example.ClubCommunity.Club.dto;

import com.example.ClubCommunity.Club.domain.ClubMember.MembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubMemberDto {
    private Long id; // 동아리 회원 ID
    private Long clubId; // 동아리 ID
    private Long memberId; // 회원 ID
    private MembershipStatus status; // 회원 상태 (신청, 승인, 거절, 탈퇴)
    private String fileName;
    private String fileType;
    private byte[] data;
}
