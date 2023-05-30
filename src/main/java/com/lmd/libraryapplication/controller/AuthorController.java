package com.lmd.libraryapplication.controller;

import com.lmd.libraryapplication.exceptions.AuthorDTONotFoundException;
import com.lmd.libraryapplication.model.dtos.AuthorDTO;
import com.lmd.libraryapplication.model.dtos.BookDTO;
import com.lmd.libraryapplication.model.repositories.AuthorRepository;
import com.lmd.libraryapplication.model.repositories.BookRepository;
import com.lmd.libraryapplication.service.AuthorService;
import com.lmd.libraryapplication.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    private BookService bookService;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors(){
        return authorRepository.findAll();
    }

    @PostMapping("/author")
    public AuthorDTO addAuthor(@RequestBody AuthorDTO authorDTO){
        return authorRepository.save(authorDTO);
    }

    @GetMapping("/author/{id}")
    public AuthorDTO getAuthorByIdPath(@PathVariable Integer id){
        return authorRepository.findById(id).get();
    }
//    @GetMapping("/author")
//    public AuthorDTO getAuthorByIdParam(@RequestParam(name = "name")String name){
//        return authorRepository.findAuthorDTOByFullName(name).get();
//    }

    /* TO BE DONE AFTER LUNCH*/
    @GetMapping("/author")
    public AuthorDTO getAuthorByIdParam(@RequestParam(name = "name")String name) throws AuthorDTONotFoundException {
        return authorRepository.findAuthorDTOByFullName(name).orElseThrow(()-> new AuthorDTONotFoundException(name));
    }


    //save a book
    @PostMapping("/book")
    public BookDTO addBookByParam(@RequestParam(name="title")String title, @RequestParam(name="authorId") Integer authorId ){
        return bookService.addBook(title,authorId);
    }

    //add book to new author

    //show all books
    @GetMapping("/books")
    public List<BookDTO> getAllBooks(){
        return bookRepository.findAll();
    }

    @PutMapping("/author/{id}")
    public AuthorDTO replaceAuthorById(@RequestBody AuthorDTO authorDTO, @PathVariable Integer id){
        if(authorRepository.findById(id).isPresent()){
            AuthorDTO oldAuthorDTO = authorRepository.findById(id).get();
            oldAuthorDTO.setFullName(authorDTO.getFullName());
            return authorRepository.save(oldAuthorDTO);
        }else{
            return authorRepository.save(authorDTO);
        }
    }
}
