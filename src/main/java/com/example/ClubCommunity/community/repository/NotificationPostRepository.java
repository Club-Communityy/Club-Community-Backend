package com.example.ClubCommunity.community.repository;

import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.community.domain.NotificationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationPostRepository extends JpaRepository<NotificationPost, Long> {

}

