package com.example.ClubCommunity.Club.domain;

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
@Table(name = "ClubDetails")
public class ClubDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 동아리 상세정보 ID

    @OneToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club; // 연관된 동아리

    private String introduction; // 동아리 소개
    private String history; // 동아리 역사

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB")
    private byte[] mainImage; // 대표 사진 데이터

    private String regularMeetingTime; // 정기 모임 시간

    private String presidentName; // 회장
    private String vicePresidentName; // 부회장
    private String treasurerName; // 총무
}
