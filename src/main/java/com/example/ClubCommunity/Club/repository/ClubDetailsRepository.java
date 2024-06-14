package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.ClubDetails;
import com.example.ClubCommunity.Club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubDetailsRepository extends JpaRepository<ClubDetails, Long> {
    Optional<ClubDetails> findByClubId(Long clubId);
    List<ClubDetails> findAllByClubId(Long clubId);  // 여러 결과를 처리할 수 있도록 메서드 추가
}
