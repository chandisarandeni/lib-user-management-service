package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.dto.MemberDTO;
import com.sarasavi.lib_user_management_service.entity.Member;
import com.sarasavi.lib_user_management_service.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ----------- basic CRUD operations added here

    // get all members
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .toList();
    }

    // get member by id
    public MemberDTO getMemberById(int memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return modelMapper.map(member, MemberDTO.class);
    }

    // add a new member
    public MemberDTO addMember(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        Member savedMember = memberRepository.save(member);
        return modelMapper.map(savedMember, MemberDTO.class);
    }

    // update an existing member
    public MemberDTO updateMember(int memberId, MemberDTO memberDTO) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Ensure ID is not overwritten
        memberDTO.setMemberId(memberId);
        modelMapper.map(memberDTO, existingMember);

        return modelMapper.map(memberRepository.save(existingMember), MemberDTO.class);
    }

    // delete a member
    public void deleteMember(int memberId) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        memberRepository.delete(existingMember);
    }

    //--------- Count related service are added here

    // total number of members
    public long getMemberCount() {
        return memberRepository.count();
    }
}
