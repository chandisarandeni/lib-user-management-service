package com.sarasavi.lib_user_management_service.controller;

import com.sarasavi.lib_user_management_service.dto.AdminDTO;
import com.sarasavi.lib_user_management_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // get all admins
    @GetMapping(path = "/admins")
    public List<AdminDTO> getAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping(path = "/admins/{adminId}")
    public AdminDTO getAdminById(@PathVariable int adminId) {
        return adminService.getAdminById(adminId);
    }
}
