package com.example.ClubCommunity.community.dto;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.community.domain.NotificationPost;
import com.example.ClubCommunity.community.domain.PhotoPost;
import com.example.ClubCommunity.community.domain.RecruitmentPost;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class RequestRecruitmentPostDto {

    private String title;
    private String content;
    private Long clubId;
    public RecruitmentPost toEntity(Club club) {
        return RecruitmentPost.builder()
                .title(title)
                .content(content)
                .club(club)
                .build();
    }
}
