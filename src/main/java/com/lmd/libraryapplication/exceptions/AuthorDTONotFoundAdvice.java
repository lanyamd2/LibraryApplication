package com.lmd.libraryapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthorDTONotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(AuthorDTONotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String authorDTONotFoundHandler(AuthorDTONotFoundException e){
        return e.getMessage();
    }
}
