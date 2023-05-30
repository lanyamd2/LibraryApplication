package com.lmd.libraryapplication;

import com.lmd.libraryapplication.model.repositories.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class LibraryApplication {
    Logger logger=Logger.getLogger("AHHHH");
    AuthorRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(AuthorRepository repository){
        return args -> logger.log(Level.SEVERE,repository.findAll().toString());
//        return args -> logger.log(Level.SEVERE, String.valueOf(repository.count()));
    }
}
