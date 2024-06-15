package com.example.ClubCommunity.community.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ResponsePhotoPostDto {
    private Long id;
    private String title;
    private byte[] image;
    private Long clubId;
    private String clubName;
}
