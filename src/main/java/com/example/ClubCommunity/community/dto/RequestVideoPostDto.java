package com.example.ClubCommunity.community.dto;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.community.domain.NotificationPost;
import com.example.ClubCommunity.community.domain.VideoPost;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class RequestVideoPostDto {
    private String title;
    private String videoUrl;
    private Long clubId;

    public VideoPost toEntity(Club club) {
        return VideoPost.builder()
                .title(title)
                .videoUrl(videoUrl)
                .club(club)
                .build();
    }
}
