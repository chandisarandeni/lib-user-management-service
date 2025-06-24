package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.dto.AdminDTO;
import com.sarasavi.lib_user_management_service.entity.Admin;
import com.sarasavi.lib_user_management_service.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    // get all admins
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(admin -> modelMapper.map(admin, AdminDTO.class))
                .toList();
    }

    // get admin by id
    public AdminDTO getAdminById(int adminId) {
        return modelMapper.map(adminRepository.findById(adminId).orElse(null), AdminDTO.class);
    }

    // create a new admin
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        Admin savedAdmin = adminRepository.save(admin);
        return modelMapper.map(savedAdmin, AdminDTO.class);
    }

    // update an existing admin
    public AdminDTO updateAdmin(int adminId, AdminDTO adminDTO) {
        Admin existingAdmin = adminRepository.findById(adminId).orElse(null);
        if (existingAdmin != null) {
            existingAdmin.setName(adminDTO.getName());
            existingAdmin.setEmail(adminDTO.getEmail());
            // Update other fields as necessary
            Admin updatedAdmin = adminRepository.save(existingAdmin);
            return modelMapper.map(updatedAdmin, AdminDTO.class);
        }
        return null; // or throw an exception
    }
}
