package com.example.ClubCommunity.Member.dto;

import com.example.ClubCommunity.Member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateDto {
    private String username;
    private String nickname;
    private String email;
    private Member.Gender gender;
    private Integer age;
    private Long stateId;
    private Long cityId;
    private Member.UserType userType;
    private MultipartFile image;
}
