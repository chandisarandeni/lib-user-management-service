package com.sarasavi.lib_user_management_service.repository;

import com.sarasavi.lib_user_management_service.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
