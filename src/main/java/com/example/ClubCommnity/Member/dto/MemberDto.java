    package com.example.ClubCommnity.Member.dto;

    import com.example.ClubCommnity.Member.domain.Member;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class MemberDto {
        // 기본적인 회원 정보 조회에 사용 : ID, 사용자 이름, 닉네임, 이메일, 성별, 나이, 그리고 사용자의 지역 정보(도와 도시)를 포함
        private Long id;
        private String username;
        private String birth;
        private Member.Gender gender;
        private String department;
        private String studentId;
        private String loginId;
        private String phoneNumber;
        private String email;
        private Member.UserType userType;
    }
