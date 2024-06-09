package com.example.ClubCommnity.Member.controller;



import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class KakaoLoginController {
    @GetMapping("/kakao-login")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+"30ade00e0da2a659ae78956bda373c62"+"&redirect_uri="+"https://localhost:8080/api/auth/kakao-login";
        model.addAttribute("location", location);

        return "login";
    }



}

//https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=30ade00e0da2a659ae78956bda373c62&redirect_uri=https://localhost:8080/api/auth/kakao-login
//여기로 와서 로그인하는거임