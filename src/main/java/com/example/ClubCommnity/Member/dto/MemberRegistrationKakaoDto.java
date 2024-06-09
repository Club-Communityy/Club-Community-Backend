package com.example.ClubCommnity.Member.dto;

import com.example.ClubCommnity.Member.domain.Member;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegistrationKakaoDto {
    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 2, max = 5, message = "사용자 이름은 2자 이상, 5자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "생년 월일을 입력해주세요.")
    @Size(min = 2, max = 10, message = "8자리로 입력해주세요 ex)20001212")
    private String birth;

    @NotNull(message = "성별을 선택해주세요.")
    private Member.Gender gender;

    @NotBlank(message = "학과를 입력해주세요.")
    @Size(min = 3, max = 15, message = "학과는 3자 이상, 15자 이하여야 합니다.")
    private String department;

    @NotBlank(message = "학번을 입력해주세요.")
    @Size(min = 6, max = 15, message = "학번은 6자 이상, 15자 이하여야 합니다.")
    private String studentId;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(min = 8, max = 15, message = "전화번호는 10자리 이상이어야 합니다.")
    private String phoneNumber;

    @NotNull(message = "사용자 유형을 선택해주세요.")
    private Member.UserType userType;

    private String code;
}
