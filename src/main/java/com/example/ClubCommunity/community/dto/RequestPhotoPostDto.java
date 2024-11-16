package com.example.ClubCommunity.community.dto;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.community.domain.PhotoPost;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@NoArgsConstructor
@Getter
public class RequestPhotoPostDto {
    @Builder
    public RequestPhotoPostDto(String title, MultipartFile image, Long clubId) {
        this.title = title;
        this.image = image;
        this.clubId = clubId;
    }

    private String title;
    private MultipartFile image;
    private Long clubId;

    public PhotoPost toEntity(Club club) {
        byte[] imageBytes = null;
        try {
            imageBytes = image != null ? image.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        return PhotoPost.builder()
                .title(title)
                .image(imageBytes)
                .club(club)
                .build();
    }
}
//dto 주석 추가 안녕 테스트 브랜치