package com.lmd.libraryapplication.service;

import com.lmd.libraryapplication.model.dtos.AuthorDTO;
import com.lmd.libraryapplication.model.dtos.BookDTO;
import com.lmd.libraryapplication.model.repositories.AuthorRepository;
import com.lmd.libraryapplication.model.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {
    Logger logger=Logger.getLogger("BOOK SERVICE");

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    public BookDTO addBook(String bookName, int authorId){
        BookDTO newBook = new BookDTO();
        if(isAuthorIdValid(authorId)){
            Optional<AuthorDTO> foundAuthor = authorRepository.findById(authorId);
            return bookRepository.save(new BookDTO(bookName,foundAuthor.get()));
        }else{
            logger.log(Level.SEVERE, "Author with ID: "+authorId+" doesn't exist");
            return newBook;
        }

    }

    public boolean isAuthorIdValid(int id){
        return !authorRepository.findById(id).isEmpty() && authorRepository.findById(id).isPresent();
    }


}
