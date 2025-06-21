package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.repository.LibrarianRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ----------- basic CRUD operations added here
}
