package com.example.ClubCommunity.Member.controller;



import com.example.ClubCommunity.Member.dto.KakaoUserInfoResponseDto;
import com.example.ClubCommunity.Member.dto.MemberRegistrationKakaoDto;
import com.example.ClubCommunity.Member.dto.TokenDto;
import com.example.ClubCommunity.Member.service.KakaoMemberService;
import com.example.ClubCommunity.Member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class KakaoLoginController {

    private final KakaoMemberService kakaoMemberService;
    private final MemberService memberService;

    //회원가입 및 로그인을 위한 코드 발급(코드를 이용해 카카오에 요청해서 토큰 발급)
    @GetMapping("/kakao-code")//리다이렉트 url
    public String getCode(@RequestParam("code") String code) {
        return code;
    }

    //코드 + 사용자가 입력한 정보를 이용해 회원가입
    @PostMapping("kakao-register")
    public ResponseEntity<TokenDto> kakaoRegister(@Valid @RequestBody MemberRegistrationKakaoDto registrationDto) throws IOException {
        String accessToken = kakaoMemberService.getAccessTokenFromKakao(registrationDto.getCode());
        KakaoUserInfoResponseDto userInfo = kakaoMemberService.getUserInfo(accessToken);

        //회원가입 로직
        TokenDto tokenDto = memberService.registerKakaoMember(registrationDto, userInfo.getKakaoAccount().getEmail());
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("kakao-login")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoMemberService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoMemberService.getUserInfo(accessToken);

        return ResponseEntity.ok(memberService.KakaologinMember(userInfo.getKakaoAccount().getEmail()));
    }
}

//http://kauth.kakao.com/oauth/authorize?response_type=code&client_id=30ade00e0da2a659ae78956bda373c62&redirect_uri=https://localhost:8080/api/auth/kakao-code
//여기로 와서 로그인하는거임