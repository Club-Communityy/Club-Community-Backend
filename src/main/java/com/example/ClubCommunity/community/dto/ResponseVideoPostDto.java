package com.example.ClubCommunity.community.dto;

import com.example.ClubCommunity.Club.domain.Club;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ResponseVideoPostDto {

    private Long id;
    private String title;
    private String videoUrl;
    private Long clubId;
}
