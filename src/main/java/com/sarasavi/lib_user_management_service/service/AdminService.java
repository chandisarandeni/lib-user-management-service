package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.dto.AdminDTO;
import com.sarasavi.lib_user_management_service.entity.Admin;
import com.sarasavi.lib_user_management_service.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // get admin by email
    public AdminDTO getAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return modelMapper.map(admin, AdminDTO.class);
        } else {
            return null; // or throw an exception if preferred
        }
    }

    // create a new admin
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        // Map DTO to Entity
        Admin admin = modelMapper.map(adminDTO, Admin.class);

        // Hash the raw password before saving
        String rawPassword = admin.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(hashedPassword);

        // Save the new admin
        Admin savedAdmin = adminRepository.save(admin);
        AdminDTO savedAdminDTO = modelMapper.map(savedAdmin, AdminDTO.class);

        return modelMapper.map(savedAdminDTO, AdminDTO.class);
    }

    // update an existing admin
    public AdminDTO updateAdmin(int adminId, AdminDTO adminDTO) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));

        // Ensure ID is not overwritten
        adminDTO.setAdminId(adminId);
        modelMapper.map(adminDTO, existingAdmin);

        // Hash the password if it has been changed
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());
            existingAdmin.setPassword(hashedPassword);
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return modelMapper.map(updatedAdmin, AdminDTO.class);
    }

    // delete an admin
    public void deleteAdmin(int adminId) {
        Admin existingAdmin = adminRepository.findById(adminId).orElse(null);
        if (existingAdmin != null) {
            adminRepository.delete(existingAdmin);
        } else {
            throw new RuntimeException("Admin not found with id: " + adminId);
        }
    }
}
