package com.example.ClubCommunity.Member.controller;



import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.dto.*;
import com.example.ClubCommunity.Member.service.KakaoMemberService;
import com.example.ClubCommunity.Member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        //회원가입 로직
        TokenDto tokenDto = memberService.registerKakaoMember(registrationDto);
        Member member = memberService.getMemberByEmail(registrationDto.getEmail());
        tokenDto.setMemberId(member.getId());
        tokenDto.setUserType(member.getUserType());
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("kakao-login")
    public ResponseEntity<TokenTypeDto> kakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoMemberService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoMemberService.getUserInfo(accessToken);
        TokenDto dto = memberService.KakaologinMember(userInfo.getKakaoAccount().getEmail());


        if (dto == null) { //db에 정보없으면 accessToken반환
            return ResponseEntity.ok(TokenTypeDto.builder()
                    .token(accessToken)
                    .type(Type.ACCESS)
                    .build());
        } else { //db에 이미 가입되어 있으면 BearerToken반환
            Member member = memberService.getMemberByEmail(userInfo.getKakaoAccount().getEmail());
            return ResponseEntity.ok(TokenTypeDto.builder()
                    .memberId(member.getId())
                    .userType(member.getUserType())
                    .token(dto.getToken())
                    .type(Type.BEARER)
                    .build());
        }
    }
}
//http://kauth.kakao.com/oauth/authorize?response_type=code&client_id=30ade00e0da2a659ae78956bda373c62&redirect_uri=https://localhost:8080/api/auth/kakao-code
//여기로 와서 로그인하는거임