package com.example.ClubCommunity.community.dto;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.community.domain.NotificationPost;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class RequestNotificationPostDto {
    private String title;
    private String content;
    private Long clubId;

    public NotificationPost toEntity(Club club) {
        return NotificationPost.builder()
                .title(title)
                .content(content)
                .club(club)
                .build();
    }
}
