package com.sarasavi.lib_user_management_service.controller;

import com.sarasavi.lib_user_management_service.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibrarianController {

    @Autowired
    private LibrarianService librarianService;

    // -------- basic CRUD operations added here --------
}
