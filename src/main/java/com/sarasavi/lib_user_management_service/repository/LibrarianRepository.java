package com.sarasavi.lib_user_management_service.repository;

import com.sarasavi.lib_user_management_service.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {
}
