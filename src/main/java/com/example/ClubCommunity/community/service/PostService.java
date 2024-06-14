package com.example.ClubCommunity.community.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.community.domain.NotificationPost;
import com.example.ClubCommunity.community.domain.PhotoPost;
import com.example.ClubCommunity.community.dto.RequestNotificationPostDto;
import com.example.ClubCommunity.community.dto.RequestPhotoPostDto;
import com.example.ClubCommunity.community.dto.ResponseNotificationPostDto;
import com.example.ClubCommunity.community.dto.ResponsePhotoPostDto;
import com.example.ClubCommunity.community.repository.NotificationPostRepository;
import com.example.ClubCommunity.community.repository.PhotoPostRepository;
import com.example.ClubCommunity.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PhotoPostRepository photoPostRepository;
    private final NotificationPostRepository notificationPostRepository;
    private final ClubRepository clubRepository;
    public ResponsePhotoPostDto photoRegist(RequestPhotoPostDto postDto) { //사진 게시글 등록
        Club club = clubRepository.findById(postDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + postDto.getClubId()));
        PhotoPost photoPost = postDto.toEntity(club);
        Long postId = photoPostRepository.save(photoPost).getId();

        byte[] imageBytes = null;
        try {
            imageBytes = postDto.getImage() != null ? postDto.getImage().getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        return ResponsePhotoPostDto.builder()
                .id(postId)
                .title(postDto.getTitle())
                .image(imageBytes)
                .clubId(postDto.getClubId())
                .build();
    }

    public List<ResponsePhotoPostDto> photoInfo() { //사진 게시글 모두 조회
        return photoPostRepository.findAll().stream()
                .map(p -> ResponsePhotoPostDto.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .image(p.getImage())
                        .clubId(p.getClub().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseNotificationPostDto notificationRegist(RequestNotificationPostDto postDto) { //부원 모집 게시글 등록
        Club club = clubRepository.findById(postDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + postDto.getClubId()));
        NotificationPost notificationPost = postDto.toEntity(club);
        Long postId = notificationPostRepository.save(notificationPost).getId();

        return ResponseNotificationPostDto.builder()
                .id(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .clubId(postDto.getClubId())
                .build();
    }

    public List<ResponseNotificationPostDto> notificationInfo() { //부원 모집 게시글 모두 조회
        return notificationPostRepository.findAll().stream()
                .map(n -> ResponseNotificationPostDto.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .content(n.getContent())
                        .clubId(n.getClub().getId())
                        .build())
                .collect(Collectors.toList());
    }

}
