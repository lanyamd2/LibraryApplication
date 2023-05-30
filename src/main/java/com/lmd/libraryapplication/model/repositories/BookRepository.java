package com.lmd.libraryapplication.model.repositories;

import com.lmd.libraryapplication.model.dtos.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookDTO, Integer> {
}