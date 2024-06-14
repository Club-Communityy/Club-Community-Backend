package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubApplicationFormRepository extends JpaRepository<ClubApplicationForm, Long> {
    Optional<ClubApplicationForm> findByClub(Club club);

    List<ClubApplicationForm> findAllByClubId(Long clubId);
}
