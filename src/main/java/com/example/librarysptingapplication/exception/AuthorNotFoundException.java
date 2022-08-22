package com.example.librarysptingapplication.exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException() {
        super("Author not found.");
    }
}
