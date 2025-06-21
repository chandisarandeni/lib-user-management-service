package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.dto.LibrarianDTO;
import com.sarasavi.lib_user_management_service.entity.Librarian;
import com.sarasavi.lib_user_management_service.repository.LibrarianRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ----------- basic CRUD operations added here

    // get all librarians
    public List<LibrarianDTO> getAllLibrarians() {
        return librarianRepository.findAll().stream()
                .map(librarian -> modelMapper.map(librarian, LibrarianDTO.class))
                .toList();
    }

    // get librarian by id
    public LibrarianDTO getLibrarianById(int librarianId) {
        return librarianRepository.findById(librarianId)
                .map(librarian -> modelMapper.map(librarian, LibrarianDTO.class))
                .orElse(null);
    }

    // add a new librarian
    public LibrarianDTO createLibrarian(LibrarianDTO librarianDTO) {
        Librarian librarian = modelMapper.map(librarianDTO, Librarian.class);
        Librarian savedLibrarian = librarianRepository.save(librarian);
        return modelMapper.map(savedLibrarian, LibrarianDTO.class);
    }

    // update existing librarian
    public LibrarianDTO updateLibrarian(int librarianId, LibrarianDTO librarianDTO) {
        Librarian existingLibrarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));

        // Ensure ID is not overwritten
        librarianDTO.setLibrarianId(librarianId);
        modelMapper.map(librarianDTO, existingLibrarian);

        return modelMapper.map(librarianRepository.save(existingLibrarian), LibrarianDTO.class);
    }

    // delete librarian by id
    public void deleteLibrarian(int librarianId) {
        if (!librarianRepository.existsById(librarianId)) {
            throw new RuntimeException("Librarian not found");
        }
        librarianRepository.deleteById(librarianId);
    }
}
