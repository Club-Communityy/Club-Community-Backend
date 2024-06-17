package com.example.ClubCommunity.community.service;

import com.example.ClubCommunity.Club.domain.Club;
import com.example.ClubCommunity.Club.domain.ClubMember;
import com.example.ClubCommunity.Club.repository.ClubMemberRepository;
import com.example.ClubCommunity.Club.repository.ClubRepository;
import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.repository.MemberRepository;
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
import com.example.ClubCommunity.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
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

    public ResponseNotificationPostDto notificationInfo(Long id, Authentication authentication) { // 공지 게시글 정보
        Member applicant = getAuthenticatedMember(authentication);
        NotificationPost r = notificationPostRepository.findById(id).get();


        if (r.getIsAccount()) { //전체 공개라면
            return ResponseNotificationPostDto.builder()
                    .id(r.getId())
                    .title(r.getTitle())
                    .content(r.getContent())
                    .image(r.getImage())
                    .clubId(r.getClub().getId())
                    .clubName(r.getClub().getName())
                    .isAccount(r.getIsAccount())
                    .build();
        } else { //전체 공개가 아니라면
            ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(applicant.getId(), r.getClub().getId())
                    .orElseThrow(() -> new UnauthorizedAccessException("해당 동아리 회원만 열람이 가능합니다."));
            if (clubMember.getStatus() == ClubMember.MembershipStatus.APPROVED) { //가입되어있는 상태라면
                return ResponseNotificationPostDto.builder()
                        .id(r.getId())
                        .title(r.getTitle())
                        .content(r.getContent())
                        .image(r.getImage())
                        .clubId(r.getClub().getId())
                        .clubName(r.getClub().getName())
                        .isAccount(r.getIsAccount())
                        .build();
            } else { // 가입 되어있지 않은 상태라면
                throw new UnauthorizedAccessException("해당 동아리 회원만 열람이 가능합니다.");
            }
        }


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

        byte[] imageBytes = null;
        try {
            imageBytes = postDto.getImage() != null ? postDto.getImage().getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        return ResponseRecruitmentPostDto.builder()
                .id(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .image(imageBytes)
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
                .image(n.getImage())
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
                        .image(n.getImage())
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

    private Member getAuthenticatedMember(Authentication authentication) {
        // 인증된 사용자 정보 가져오기
        String loginId = authentication.getName();
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 로그인 ID로 회원을 찾을 수 없습니다: " + loginId));
    }
}
