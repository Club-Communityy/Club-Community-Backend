package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.ClubDetails;
import com.example.ClubCommunity.Club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubDetailsRepository extends JpaRepository<ClubDetails, Long> {
    Optional<ClubDetails> findByClub(Club club);
    Optional<ClubDetails> findByClubId(Long clubId);
}
