package com.lmd.libraryapplication.controller;

import com.lmd.libraryapplication.exceptions.AuthorDTONotFoundException;
import com.lmd.libraryapplication.model.dtos.AuthorDTO;
import com.lmd.libraryapplication.model.dtos.BookDTO;
import com.lmd.libraryapplication.model.repositories.AuthorRepository;
import com.lmd.libraryapplication.model.repositories.BookRepository;
import com.lmd.libraryapplication.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class LibraryController {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public LibraryController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/author/{id}")
    public HttpEntity<AuthorDTO> getAuthorById (@PathVariable Integer id){
        AuthorDTO authorDTO = authorRepository.findById(id).get();
//        authorDTO.add(linkTo(methodOn(LibraryController.class).getAuthorById(id)).withSelfRel());//gives link to self
        authorDTO.add(linkTo(methodOn(LibraryController.class).getAuthorById(id)).withRel("George Orwell"));//changes link name to String "George Orwell"
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }


    @GetMapping("/book/{id}")
    public HttpEntity<BookDTO> getBookById (@PathVariable Integer id){
        BookDTO bookDTO =bookRepository.findById(id).get();
        bookDTO.add(linkTo(methodOn(LibraryController.class).getBookById(id)).withSelfRel());//gives link to self
//        bookDTO.add(linkTo(methodOn(LibraryController.class).getBookById(id)).withRel("George Orwell"));//changes link name to String "George Orwell"
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("/author/{id}/books")
    public CollectionModel<BookDTO> getAllBooksByAuthorId(@PathVariable Integer id){
        List<BookDTO> books= authorRepository.findById(id).get().getBooks();
        for (BookDTO bookDTO: books){
            Link link = linkTo(methodOn(LibraryController.class).getBookById(bookDTO.getId())).withSelfRel();
            bookDTO.add(link);
        }
        Link link2 = linkTo(methodOn(LibraryController.class).getAllBooksByAuthorId(id)).withSelfRel();
        return CollectionModel.of(books,link2);
    }

    @GetMapping("/authors")
    public CollectionModel<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> authors = authorRepository.findAll();
        for(AuthorDTO authorDTO: authors){
            authorDTO.add(linkTo(methodOn(LibraryController.class).getAuthorById(authorDTO.getId())).withSelfRel());
            authorDTO.add(linkTo(methodOn(LibraryController.class).getAllBooksByAuthorId(authorDTO.getId())).withRel("books"));
        }
        return CollectionModel.of(authors, linkTo(LibraryController.class).withSelfRel());
    }



}
