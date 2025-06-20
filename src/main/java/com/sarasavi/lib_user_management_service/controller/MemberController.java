package com.sarasavi.lib_user_management_service.controller;

import com.sarasavi.lib_user_management_service.dto.MemberDTO;
import com.sarasavi.lib_user_management_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
