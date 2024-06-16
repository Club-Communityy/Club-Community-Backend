package com.example.ClubCommunity.Member.controller;

import com.example.ClubCommunity.Member.domain.Member;
import com.example.ClubCommunity.Member.dto.*;
import com.example.ClubCommunity.Member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> registerMember(@Valid @RequestBody MemberRegistrationDto registrationDto) {
        TokenDto tokenDto = memberService.registerMember(registrationDto);
        Member member = memberService.getMemberByEmail(registrationDto.getEmail());
        tokenDto.setMemberId(member.getId());
        tokenDto.setUserType(member.getUserType());
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberLoginDto loginDto) {
        TokenDto tokenDto = memberService.loginMember(loginDto);
        Member member = memberService.getMemberLoginId(loginDto.getLoginId());
        tokenDto.setMemberId(member.getId());
        tokenDto.setUserType(member.getUserType());
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("Authentication is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!authentication.isAuthenticated()) {
            System.out.println("Authentication is not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            System.out.println("Principal is not an instance of UserDetails");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Authenticated username: " + userDetails.getUsername());

        Object memberDto;
        if (userDetails.getUsername().contains("@")) {
            memberDto = memberService.getKakaoMemberDetailsByUsername(userDetails.getUsername());
        } else {
            memberDto = memberService.getMemberDetailsByUsername(userDetails.getUsername());
        }

        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("/update-profile/{id}")
    public ResponseEntity<MemberDto> updateMemberProfile(@PathVariable("id") Long id, @Valid @RequestBody MemberUpdateDto updateDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberDto updatedMember = memberService.updateMemberDetails(id, updateDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedMember);
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") Long id,
                                            @RequestParam("currentPassword") String currentPassword,
                                            @RequestParam("newPassword") String newPassword,
                                            @RequestParam("confirmPassword") String confirmPassword) {
        memberService.changeMemberPassword(id, currentPassword, newPassword, confirmPassword);
        return ResponseEntity.ok("비밀번호가 성공적으로 업데이트되었습니다.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        memberService.deleteMember(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }
    @GetMapping("/check-admin")
    public ResponseEntity<String> checkIfUserIsAdmin(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "").trim();
        boolean isAdmin = memberService.checkIfUserIsAdmin(token);
        if (isAdmin) {
            return ResponseEntity.ok("관리자입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자만 접근 가능합니다.");
        }
    }

    @GetMapping("/api/auth/check-join/{clubId}")
    public Boolean checkJoinClub(@PathVariable("clubId") Long clubId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return memberService.checkJoinClub(clubId, userDetails.getUsername());
    }
    
}
