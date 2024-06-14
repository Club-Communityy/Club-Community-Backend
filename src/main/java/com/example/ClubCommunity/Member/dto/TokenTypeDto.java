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
public class TokenTypeDto {
    private Long memberId;
    private UserType userType;
    private String token;
    private Type type;

}

