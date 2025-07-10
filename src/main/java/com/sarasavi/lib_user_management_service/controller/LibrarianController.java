package com.sarasavi.lib_user_management_service.controller;

import com.sarasavi.lib_user_management_service.dto.LibrarianDTO;
import com.sarasavi.lib_user_management_service.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/")
public class LibrarianController {

    @Autowired
    private LibrarianService librarianService;

    // -------- basic CRUD operations added here --------

    // get all librarians
    @GetMapping(path = "/librarians")
    public List<LibrarianDTO> getAllLibrarians() {
        return librarianService.getAllLibrarians();
    }

    // get librarian by id
    @GetMapping(path = "/librarians/{librarianId}")
    public LibrarianDTO getLibrarianById(@PathVariable("librarianId") int librarianId) {
        return librarianService.getLibrarianById(librarianId);
    }

    // add a new librarian
    @PostMapping(path = "/librarians")
    public LibrarianDTO createLibrarian(@RequestBody LibrarianDTO librarianDTO) {
        return librarianService.createLibrarian(librarianDTO);
    }

    // update existing librarian
    @PutMapping(path = "/librarians/{librarianId}")
    public LibrarianDTO updateLibrarian(@PathVariable("librarianId") int librarianId, @RequestBody LibrarianDTO librarianDTO) {
        return librarianService.updateLibrarian(librarianId, librarianDTO);
    }

    // delete librarian by id
    @DeleteMapping(path = "/librarians/{librarianId}")
    public void deleteLibrarian(@PathVariable("librarianId") int librarianId) {
        librarianService.deleteLibrarian(librarianId);
    }
}
