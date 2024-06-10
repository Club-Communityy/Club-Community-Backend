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
@Table(name = "ClubApplicationForm")
public class ClubApplicationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 신청서 ID

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club; // 연관된 동아리

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 연관된 회원

    @Column(nullable = false)
    private String fileName; // 파일 이름

    @Column(nullable = false)
    private String fileType; // 파일 타입

    @Lob
    @Column(name = "data", columnDefinition="LONGBLOB")
    private byte[] data; // 파일 데이터
}
