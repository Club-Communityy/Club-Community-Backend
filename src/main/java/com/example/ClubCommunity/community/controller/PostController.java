package com.example.ClubCommunity.community.controller;

import com.example.ClubCommunity.community.dto.*;
import com.example.ClubCommunity.community.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    @PostMapping("/photo/regist") //사진 게시글 등록
    public ResponseEntity<ResponsePhotoPostDto> photoRegist(@ModelAttribute RequestPhotoPostDto postDto) {
        ResponsePhotoPostDto responsePhotoPostDto = postService.photoRegist(postDto);
        return ResponseEntity.ok(responsePhotoPostDto);
    }
    @GetMapping("/photo") //사진 게시글 전체 조회
    public ResponseEntity<List<ResponsePhotoPostDto>> photoAllInfo() {
        List<ResponsePhotoPostDto> responsePhotoPostDto = postService.photoAllInfo();
        return ResponseEntity.ok(responsePhotoPostDto);
    }
    @GetMapping("/photo/{id}") //사진 게시글 정보
    public ResponseEntity<ResponsePhotoPostDto> photoInfo(@PathVariable("id") Long id) {
        ResponsePhotoPostDto responsePhotoPostDto = postService.photoInfo(id);
        return ResponseEntity.ok(responsePhotoPostDto);
    }



    @PostMapping("/recruitment/regist") //부원 모집 게시글 등록
    public ResponseEntity<ResponseRecruitmentPostDto> recruitmentRegist(@RequestBody RequestRecruitmentPostDto postDto) {
        ResponseRecruitmentPostDto responseNotificationPostDto = postService.recruitmentRegist(postDto);
        return ResponseEntity.ok(responseNotificationPostDto);
    }
    @GetMapping("/recruitment") //부원 모집 게시글 전체 조회
    public ResponseEntity<List<ResponseRecruitmentPostDto>> recruitmentAllInfo() {
        List<ResponseRecruitmentPostDto> responseNotificationPostDto = postService.recruitmentAllInfo();
        return ResponseEntity.ok(responseNotificationPostDto);
    }
    @GetMapping("/recruitment/{id}") //부원 모집 게시글 정보
    public ResponseEntity<ResponseRecruitmentPostDto> recruitmentInfo(@PathVariable("id") Long id) {
        ResponseRecruitmentPostDto responseNotificationPostDto = postService.recruitmentInfo(id);
        return ResponseEntity.ok(responseNotificationPostDto);
    }



    @PostMapping("/notification/regist") //공지 게시글 등록
    public ResponseEntity<ResponseNotificationPostDto> notificationRegist(@ModelAttribute RequestNotificationPostDto postDto) {
        ResponseNotificationPostDto responseNotificationPostDto = postService.notificationRegist(postDto);
        return ResponseEntity.ok(responseNotificationPostDto);
    }
    @GetMapping("/notification") //공지 게시글 전체 조회
    public ResponseEntity<List<ResponseNotificationPostDto>> notificationAllInfo() {
        List<ResponseNotificationPostDto> responseNotificationPostDto = postService.notificationAllInfo();
        return ResponseEntity.ok(responseNotificationPostDto);
    }
    @GetMapping("/notification/{id}") //공지 게시글 정보
    public ResponseEntity<ResponseNotificationPostDto> notificationInfo(@PathVariable("id") Long id) {
        ResponseNotificationPostDto responseNotificationPostDto = postService.notificationInfo(id);
        return ResponseEntity.ok(responseNotificationPostDto);
    }
    @PostMapping("/notification/{id}") //공지 게시글 정보
    public ResponseEntity<ResponseNotificationPostDto> notificationInfo(@PathVariable("id") Long id, @RequestBody ToggleDto dto) {
        ResponseNotificationPostDto responseNotificationPostDto = postService.updateIsAccount(id, dto);
        return ResponseEntity.ok(responseNotificationPostDto);
    }




    @PostMapping("/video/regist") //영상 게시글 등록
    public ResponseEntity<ResponseVideoPostDto> videoRegist(@RequestBody RequestVideoPostDto postDto) {
        ResponseVideoPostDto responseVideoPostDto = postService.videoRegist(postDto);
        return ResponseEntity.ok(responseVideoPostDto);
    }
    @GetMapping("/video") //영상 게시글 전체 조회
    public ResponseEntity<List<ResponseVideoPostDto>> videoAllInfo() {
        List<ResponseVideoPostDto> responseVideoPostDto = postService.videoAllInfo();
        return ResponseEntity.ok(responseVideoPostDto);
    }
    @GetMapping("/video/{id}") //영상 게시글 정보
    public ResponseEntity<ResponseVideoPostDto> videoInfo(@PathVariable("id") Long id) {
        ResponseVideoPostDto responseVideoPostDto = postService.videoInfo(id);
        return ResponseEntity.ok(responseVideoPostDto);
    }


}
