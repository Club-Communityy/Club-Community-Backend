package com.example.ClubCommunity.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenTypeDto {
    private Long memberId;
    private String token;
    private Type type;

}

