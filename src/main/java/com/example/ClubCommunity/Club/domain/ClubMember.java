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
@Table(name = "ClubMember")
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 동아리 회원 ID

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club; // 연관된 동아리

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 연관된 회원

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status; // 회원 상태 (신청, 승인, 거절, 탈퇴)

    public enum MembershipStatus {
        APPLIED, APPROVED, REJECTED, WITHDRAWN
    }
}
