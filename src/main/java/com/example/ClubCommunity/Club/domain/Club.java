package com.example.ClubCommunity.Club.domain;

import com.example.ClubCommunity.Member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 동아리 ID

    @Column(nullable = false, length = 50)
    private String name; // 동아리 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubType type; // 동아리 종류 (중앙, 학과)

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Member applicant; // 신청자

    @Column(nullable = false)
    private String advisorName; // 지도교수 이름

    @Column(nullable = false)
    private String advisorMajor; // 지도교수 전공

    @Column(nullable = false)
    private String advisorContact; // 지도교수 연락처

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubStatus status; // 동아리 신청 상태 (검토, 승인, 거절)

    @Column(nullable = true)
    private String rejectionReason; // 거절 사유

    public enum ClubType {
        CENTRAL, DEPARTMENTAL // 동아리 종류 (중앙, 학과)
    }

    public enum ClubStatus {
        PENDING, APPROVED, REJECTED // 동아리 신청 상태 (검토, 승인, 거절)
    }
}
