package com.example.ClubCommunity.community.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.community.domain.NotificationPost;
import com.example.ClubCommunity.community.domain.PhotoPost;
import com.example.ClubCommunity.community.domain.RecruitmentPost;
import com.example.ClubCommunity.community.domain.VideoPost;
import com.example.ClubCommunity.community.dto.*;
import com.example.ClubCommunity.community.repository.NotificationPostRepository;
import com.example.ClubCommunity.community.repository.PhotoPostRepository;
import com.example.ClubCommunity.community.repository.RecruitmentPostRepository;
import com.example.ClubCommunity.community.repository.VideoPostRepository;
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
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final VideoPostRepository videoPostRepository;
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
                .clubName(club.getName())
                .build();
    }

    public ResponsePhotoPostDto photoInfo(Long id) { //사진 게시글 정보 조회
        PhotoPost p = photoPostRepository.findById(id).get();
        return ResponsePhotoPostDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .image(p.getImage())
                .clubId(p.getClub().getId())
                .clubName(p.getClub().getName())
                .build();

    }

    public List<ResponsePhotoPostDto> photoAllInfo() { //사진 게시글 모두 조회
        return photoPostRepository.findAll().stream()
                .map(p -> ResponsePhotoPostDto.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .image(p.getImage())
                        .clubId(p.getClub().getId())
                        .clubName(p.getClub().getName())
                        .build())
                .collect(Collectors.toList());
    }






    public ResponseNotificationPostDto notificationRegist(RequestNotificationPostDto postDto) { // 공지 게시글 등록
        Club club = clubRepository.findById(postDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + postDto.getClubId()));
        NotificationPost notificationPost = postDto.toEntity(club);
        Long postId = notificationPostRepository.save(notificationPost).getId();

        byte[] imageBytes = null;
        try {
            imageBytes = postDto.getImage() != null ? postDto.getImage().getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        return ResponseNotificationPostDto.builder()
                .id(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .image(imageBytes)
                .clubId(postDto.getClubId())
                .clubName(club.getName())
                .isAccount(postDto.getIsAccount())
                .build();
    }

    public ResponseNotificationPostDto notificationInfo(Long id) { // 공지 게시글 정보
        NotificationPost r = notificationPostRepository.findById(id).get();
        return ResponseNotificationPostDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .content(r.getContent())
                .image(r.getImage())
                .clubId(r.getClub().getId())
                .clubName(r.getClub().getName())
                .isAccount(r.getIsAccount())
                .build();
    }
    public List<ResponseNotificationPostDto> notificationAllInfo() { // 공지 게시글 모두 조회
        return notificationPostRepository.findAll().stream()
                .map(r -> ResponseNotificationPostDto.builder()
                        .id(r.getId())
                        .title(r.getTitle())
                        .content(r.getContent())
                        .image(r.getImage())
                        .clubId(r.getClub().getId())
                        .clubName(r.getClub().getName())
                        .isAccount(r.getIsAccount())
                        .build())
                .collect(Collectors.toList());
    }


    public ResponseRecruitmentPostDto recruitmentRegist(RequestRecruitmentPostDto postDto) { //부원 모집 게시글 등록
        Club club = clubRepository.findById(postDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + postDto.getClubId()));
        RecruitmentPost recruitmentPost = postDto.toEntity(club);
        Long postId = recruitmentPostRepository.save(recruitmentPost).getId();

        return ResponseRecruitmentPostDto.builder()
                .id(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .clubId(postDto.getClubId())
                .clubName(club.getName())
                .build();
    }

    public ResponseRecruitmentPostDto recruitmentInfo(Long id) { //부원 모집 게시글 정보
        RecruitmentPost n = recruitmentPostRepository.findById(id).get();
        return ResponseRecruitmentPostDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .clubId(n.getClub().getId())
                .clubName(n.getClub().getName())
                .build();
    }

    public List<ResponseRecruitmentPostDto> recruitmentAllInfo() { //부원 모집 게시글 모두 조회
        return recruitmentPostRepository.findAll().stream()
                .map(n -> ResponseRecruitmentPostDto.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .content(n.getContent())
                        .clubId(n.getClub().getId())
                        .clubName(n.getClub().getName())
                        .build())
                .collect(Collectors.toList());
    }



    public ResponseVideoPostDto videoRegist(RequestVideoPostDto postDto) { //영상 게시글 등록
        Club club = clubRepository.findById(postDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 동아리를 찾을 수 없습니다: " + postDto.getClubId()));
        VideoPost videoPost = postDto.toEntity(club);
        Long postId = videoPostRepository.save(videoPost).getId();

        return ResponseVideoPostDto.builder()
                .id(postId)
                .title(postDto.getTitle())
                .videoUrl(postDto.getVideoUrl())
                .clubId(postDto.getClubId())
                .clubName(club.getName())
                .build();
    }

    public List<ResponseVideoPostDto> videoAllInfo() { //영상 게시글 모두 조회
        return videoPostRepository.findAll().stream()
                .map(v -> ResponseVideoPostDto.builder()
                        .id(v.getId())
                        .title(v.getTitle())
                        .videoUrl(v.getVideoUrl())
                        .clubId(v.getClub().getId())
                        .clubName(v.getClub().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseVideoPostDto videoInfo(Long id) { //영상 게시글 정보
        VideoPost v = videoPostRepository.findById(id).get();
        return ResponseVideoPostDto.builder()
                .id(v.getId())
                .title(v.getTitle())
                .videoUrl(v.getVideoUrl())
                .clubId(v.getClub().getId())
                .clubName(v.getClub().getName())
                .build();
    }

    public ResponseNotificationPostDto updateIsAccount(Long id, ToggleDto dto) {
        NotificationPost notificationPost = notificationPostRepository.findById(id).get();
        notificationPost.updateIsAccount(dto.getIsAccount());

        return ResponseNotificationPostDto.builder()
                .id(notificationPost.getId())
                .title(notificationPost.getTitle())
                .content(notificationPost.getContent())
                .image(notificationPost.getImage())
                .clubId(notificationPost.getClub().getId())
                .clubName(notificationPost.getClub().getName())
                .isAccount(notificationPost.getIsAccount())
                .build();


    }
}
