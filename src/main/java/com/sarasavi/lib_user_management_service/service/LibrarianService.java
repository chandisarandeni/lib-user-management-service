package com.sarasavi.lib_user_management_service.service;

import com.sarasavi.lib_user_management_service.dto.LibrarianDTO;
import com.sarasavi.lib_user_management_service.entity.Librarian;
import com.sarasavi.lib_user_management_service.repository.LibrarianRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        // Map DTO to Entity
        Librarian librarian = modelMapper.map(librarianDTO, Librarian.class);

        // Hash the raw password before saving
        String rawPassword = librarian.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        librarian.setPassword(hashedPassword);

        // Save the new librarian
        Librarian savedLibrarian = librarianRepository.save(librarian);
        LibrarianDTO savedLibrarianDTO = modelMapper.map(savedLibrarian, LibrarianDTO.class);

        return modelMapper.map(savedLibrarianDTO, LibrarianDTO.class);
    }

    // update existing librarian
    public LibrarianDTO updateLibrarian(int librarianId, LibrarianDTO librarianDTO) {
        Librarian existingLibrarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));

        // Update fields
        existingLibrarian.setName(librarianDTO.getName());
        existingLibrarian.setName(librarianDTO.getName());
        existingLibrarian.setNic(librarianDTO.getNic());
        existingLibrarian.setEmail(librarianDTO.getEmail());
        existingLibrarian.setPhoneNumber(librarianDTO.getPhoneNumber());
        existingLibrarian.setAddress(librarianDTO.getAddress());

        // If password is provided, hash it and update
        if (librarianDTO.getPassword() != null && !librarianDTO.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(librarianDTO.getPassword());
            existingLibrarian.setPassword(hashedPassword);
        }

        Librarian updatedLibrarian = librarianRepository.save(existingLibrarian);
        return modelMapper.map(updatedLibrarian, LibrarianDTO.class);
    }

    // delete librarian by id
    public void deleteLibrarian(int librarianId) {
        if (!librarianRepository.existsById(librarianId)) {
            throw new RuntimeException("Librarian not found");
        }
        librarianRepository.deleteById(librarianId);
    }
}
