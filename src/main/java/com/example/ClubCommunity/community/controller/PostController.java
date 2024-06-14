package com.example.ClubCommunity.community.controller;

import com.example.ClubCommunity.community.dto.RequestNotificationPostDto;
import com.example.ClubCommunity.community.dto.RequestPhotoPostDto;
import com.example.ClubCommunity.community.dto.ResponseNotificationPostDto;
import com.example.ClubCommunity.community.dto.ResponsePhotoPostDto;
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
    @GetMapping("/photo") //사진 게시글 조회
    public ResponseEntity<List<ResponsePhotoPostDto>> photoInfo() {
        List<ResponsePhotoPostDto> responsePhotoPostDto = postService.photoInfo();
        return ResponseEntity.ok(responsePhotoPostDto);
    }

    @PostMapping("/notification/regist") //부원 모집 게시글 등록
    public ResponseEntity<ResponseNotificationPostDto> notificationRegist(@RequestBody RequestNotificationPostDto postDto) {
        ResponseNotificationPostDto responseNotificationPostDto = postService.notificationRegist(postDto);
        return ResponseEntity.ok(responseNotificationPostDto);
    }
    @GetMapping("/notification") //부원 모집 게시글 조회
    public ResponseEntity<List<ResponseNotificationPostDto>> notificationInfo() {
        List<ResponseNotificationPostDto> responseNotificationPostDto = postService.notificationInfo();
        return ResponseEntity.ok(responseNotificationPostDto);
    }
}
