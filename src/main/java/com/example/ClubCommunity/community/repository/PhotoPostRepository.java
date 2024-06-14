package com.example.ClubCommunity.community.repository;


import com.example.ClubCommunity.community.domain.PhotoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoPostRepository extends JpaRepository<PhotoPost, Long> {

}
