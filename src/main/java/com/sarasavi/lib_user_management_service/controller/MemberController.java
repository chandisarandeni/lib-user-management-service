package com.sarasavi.lib_user_management_service.controller;

import com.sarasavi.lib_user_management_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;


}
