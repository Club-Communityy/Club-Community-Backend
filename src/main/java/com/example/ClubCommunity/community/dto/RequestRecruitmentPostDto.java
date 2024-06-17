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
    private MultipartFile image;
    private Long clubId;
    public RecruitmentPost toEntity(Club club) {

        byte[] imageBytes = null;
        try {
            imageBytes = image != null ? image.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        return RecruitmentPost.builder()
                .title(title)
                .content(content)
                .image(imageBytes)
                .club(club)
                .build();
    }
}
