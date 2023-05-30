package com.lmd.libraryapplication.model.repositories;

import com.lmd.libraryapplication.model.dtos.AuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorDTO, Integer> {

    @Query(value = "SELECT * FROM library.authors where full_name=:name", nativeQuery = true)//native SQL
    Optional<AuthorDTO> findByNameSQL(String name);

    @Query("select a from AuthorDTO a where a.fullName=:name")
    Optional<AuthorDTO> findByNameJPQL(String name);//Java Persistence Query Language

    Optional<AuthorDTO> findAuthorDTOByFullName(String name);//Spring expression language

    Optional<AuthorDTO> findAuthorDTOByFullNameAndId(String name, Integer id);

}

