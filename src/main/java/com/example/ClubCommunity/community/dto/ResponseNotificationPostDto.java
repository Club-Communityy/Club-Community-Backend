package com.example.ClubCommunity.community.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ResponseNotificationPostDto {
    private Long id;
    private String title;
    private String content;
    private Long clubId;
}
