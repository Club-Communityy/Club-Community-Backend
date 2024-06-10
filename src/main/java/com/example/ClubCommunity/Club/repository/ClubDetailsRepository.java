package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.ClubDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDetailsRepository extends JpaRepository<ClubDetails, Long> {
}
