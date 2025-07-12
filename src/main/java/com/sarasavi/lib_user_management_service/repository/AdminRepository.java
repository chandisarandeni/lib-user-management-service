package com.sarasavi.lib_user_management_service.repository;

import com.sarasavi.lib_user_management_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // find admin by email
    @Query("SELECT a FROM Admin a WHERE a.email = ?1")
    Admin findByEmail(String email);
}
