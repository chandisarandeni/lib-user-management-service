package com.sarasavi.lib_user_management_service.controller;

import com.sarasavi.lib_user_management_service.dto.MemberDTO;
import com.sarasavi.lib_user_management_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // -------- basic CRUD operations added here --------

    // get all members
    @GetMapping(path = "/members")
    public List<MemberDTO> getMembers() {
        return memberService.getAllMembers();
    }

    // get member by id
    @GetMapping(path = "/members/{memberId}")
    public MemberDTO getMemberById(@PathVariable("memberId") int memberId) {
        return memberService.getMemberById(memberId);
    }

    // get member by email
    @GetMapping(path = "/members/by-email")
    public MemberDTO getMemberByEmail(@RequestParam String email) {
        return memberService.getMemberByEmail(email);
    }

    // add a new member
    @PostMapping(path = "/members")
    public MemberDTO addMember(@RequestBody MemberDTO memberDTO) {
        return memberService.addMember(memberDTO);
    }

    // update an existing member
    @PutMapping(path = "/members/{memberId}")
    public MemberDTO updateMember(@PathVariable("memberId") int memberId, @RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(memberId, memberDTO);
    }

    // reset member password
    // sends reset password link to member's email
    @PostMapping(path = "/members/send-reset-password-link")
    public void sendResetPasswordLink(@RequestParam String email) {
        memberService.sendResetPasswordLink(email);
    }


    // put otpCode as a request parameter -> check otpSendTime, and it is valid till only 5 minutes
    // send email, OTP code and new password as req body
    @PostMapping(path = "/members/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String otpCode,
            @RequestParam String newPassword
    ) {
        memberService.resetMemberPassword(email, otpCode, newPassword);
        return ResponseEntity.ok("Password reset successful.");
    }

    // delete a member
    @DeleteMapping(path = "/members/{memberId}")
    public void deleteMember(@PathVariable("memberId") int memberId) {
        memberService.deleteMember(memberId);
    }

    //-------- count related operations added here --------
    @GetMapping(path = "/members/count")
    public long getMemberCount() {
        return memberService.getMemberCount();
    }
}
