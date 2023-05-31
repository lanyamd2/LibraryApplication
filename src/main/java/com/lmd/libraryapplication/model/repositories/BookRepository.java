package com.lmd.libraryapplication.model.repositories;

import com.lmd.libraryapplication.model.dtos.AuthorDTO;
import com.lmd.libraryapplication.model.dtos.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BookRepository extends JpaRepository<BookDTO, Integer> {
    @Query(value = "SELECT b.author_id FROM books b WHERE b.book_id = :id", nativeQuery = true)
    Map<String, Integer> getAuthorIdByBookId(int id);
}