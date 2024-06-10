package com.example.ClubCommunity.Club.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String mainImage; // 대표 사진 URL
    private String regularMeetingTime; // 정기 모임 시간

    @ElementCollection
    @CollectionTable(name = "club_officers", joinColumns = @JoinColumn(name = "club_id"))
    @Column(name = "officer")
    private List<String> officers; // 임원 명단 (대표, 부대표, 총무 등)
}
