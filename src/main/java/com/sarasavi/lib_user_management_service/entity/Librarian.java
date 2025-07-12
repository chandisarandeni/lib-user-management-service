package com.sarasavi.lib_user_management_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "librarian")
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "librarian_id")
    private int librarianId;

    @Column(name = "name")
    private String name;

    @Column(name = "nic")
    private String nic;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "shift")
    private String shift;

    @Column(name = "experience")
    private String experience;

    @Column(name = "join_date")
    private String joinDate;

    @Column(name = "password")
    private String password;
}
