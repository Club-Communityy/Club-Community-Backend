package com.example.ClubCommunity.community.domain;

import com.example.ClubCommunity.Club.domain.Club;
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
public class RecruitmentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB",nullable = true)
    private byte[] image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
}


