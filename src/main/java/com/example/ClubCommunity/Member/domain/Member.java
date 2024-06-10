package com.example.ClubCommunity.Member.domain;

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
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false, unique = true)
    private String studentId;

    @Column(nullable = true, unique = true)
    private String loginId;

    @Column(name = "password_hash", nullable = true)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public enum UserType {
        ROLE_USER, ROLE_ADMIN, ROLE_CLUBMANAGER
    }

    public enum Gender {
        MALE, FEMALE
    }
}
