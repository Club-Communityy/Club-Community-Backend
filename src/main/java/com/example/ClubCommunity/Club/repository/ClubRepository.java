package com.example.ClubCommunity.Club.repository;

import com.example.ClubCommunity.Club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByStatus(Club.ClubStatus status);
}
