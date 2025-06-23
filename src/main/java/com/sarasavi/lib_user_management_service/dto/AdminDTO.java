package com.sarasavi.lib_user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private int adminId;
    private String name;
    private String nic;
    private String email;
    private String phoneNumber;
    private String address;
}
