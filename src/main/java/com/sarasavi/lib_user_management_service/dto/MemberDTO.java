package com.sarasavi.lib_user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private int memberId;
    private String name;
    private String nic;
    private String email;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String profilePicture;
    private String password;
    private String otpCode;
    private String otpSendTime;
}
