package com.sarasavi.lib_user_management_service.repository;

import com.sarasavi.lib_user_management_service.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // find member by email
    @Query("SELECT m FROM Member m WHERE m.email = ?1")
    Member findMemberByEmail(String email);
}
