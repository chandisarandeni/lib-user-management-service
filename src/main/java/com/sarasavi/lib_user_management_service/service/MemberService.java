package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.dto.MemberDTO;
import com.sarasavi.lib_user_management_service.entity.Member;
import com.sarasavi.lib_user_management_service.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // get member by email
    public MemberDTO getMemberByEmail(String email) {
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new RuntimeException("Member not found with email: " + email);
        }
        return modelMapper.map(member, MemberDTO.class);
    }

    // add a new member
    public MemberDTO addMember(MemberDTO memberDTO) {
        // Map DTO to Entity
        Member member = modelMapper.map(memberDTO, Member.class);

        // Hash the raw password before saving
        String rawPassword = member.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(hashedPassword);

        // Save the new member
        Member savedMember = memberRepository.save(member);
        MemberDTO savedDTO = modelMapper.map(savedMember, MemberDTO.class);

        // Send welcome email (via POST request)
        try {
            // Create request body as a Map
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("memberId", savedDTO.getMemberId());
            requestBody.put("name", savedDTO.getName());
            requestBody.put("email", savedDTO.getEmail());
            requestBody.put("password", rawPassword); // ⚠️ Avoid in production, better to send a reset link

            // Send POST request
            restTemplate.postForObject(
                    "http://localhost:3000/api/v1/send-welcome-mail",
                    requestBody,
                    String.class
            );

            System.out.println("✅ Welcome email sent to: " + savedDTO.getEmail());

        } catch (Exception e) {
            System.err.println("❌ Failed to send welcome email: " + e.getMessage());
        }

        return savedDTO;
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

    // reset member password
    // PUT otpCode as a request parameter -> check otpSendTime, and it is valid till only 5 minutes
    // Send email, OTP code, and new password as req body
    public void resetMemberPassword(String email, String otpCode, String newPassword) {
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new RuntimeException("Member not found with email: " + email);
        }

        // Check if OTP code is valid and not expired
        long currentTime = System.currentTimeMillis();
        long otpSendTime = Long.parseLong(member.getOtpSendTime());
        if (!otpCode.equals(member.getOtpCode()) || (currentTime - otpSendTime > 5 * 60 * 1000)) {
            throw new RuntimeException("Invalid or expired OTP code");
        }

        // Hash the new password before saving
        String hashedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(hashedPassword);

        // Clear OTP code and send time after successful reset
        member.setOtpCode(null);
        member.setOtpSendTime(null);

        memberRepository.save(member);

        // Optionally, send confirmation email (not implemented here)
        System.out.println("✅ Password successfully reset for member: " + email);
    }

    // send a reset password link
    // to -> http://localhost:3000/api/v1/send-reset-password-link
    // send a reset password link
    public void sendResetPasswordLink(String email) {
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new RuntimeException("Member not found with email: " + email);
        }

        // generate and store OTP code and send time
        String otpCode = String.valueOf((int) (Math.random() * 9000) + 1000); // 4-digit OTP
        member.setOtpCode(otpCode);
        member.setOtpSendTime(String.valueOf(System.currentTimeMillis())); // current time in milliseconds
        memberRepository.save(member);

        // Send reset password link via POST request
        try {
            // Create request body as a Map
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("otpCode", otpCode);

            // Update endpoint to match the intended one
            restTemplate.postForObject(
                    "http://localhost:3000/api/v1/send-reset-password-link", // Correct endpoint
                    requestBody,
                    String.class
            );
            System.out.println("✅ Reset password link sent to: " + email);
        } catch (Exception e) {
            System.err.println("❌ Failed to send reset password link: " + e.getMessage());
        }
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
