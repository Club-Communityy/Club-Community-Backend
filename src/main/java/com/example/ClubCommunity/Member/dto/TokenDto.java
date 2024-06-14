package com.example.ClubCommunity.Member.dto;

import com.example.ClubCommunity.Member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.ClubCommunity.Member.domain.Member.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    // 인증 성공 시 사용자에게 반환되는 JWT 토큰을 담는 데 사용
    private Long memberId;
    private UserType userType;
    private String token;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
