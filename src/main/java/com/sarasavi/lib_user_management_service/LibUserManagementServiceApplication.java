// User Guide:
// ***************************************************************************
// **** Please make sure to follow the steps below to run the application ****
// ***************************************************************************
// Database creation script: CREATE DATABASE lib_user_management_db;

package com.sarasavi.lib_user_management_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibUserManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibUserManagementServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
