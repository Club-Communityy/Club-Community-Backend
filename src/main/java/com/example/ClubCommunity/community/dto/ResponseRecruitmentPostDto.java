package com.example.ClubCommunity.community.dto;

import com.example.ClubCommunity.Club.domain.Club;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ResponseRecruitmentPostDto {

    private Long id;
    private String title;
    private String content;
    private Long clubId;
    private String clubName;
}
