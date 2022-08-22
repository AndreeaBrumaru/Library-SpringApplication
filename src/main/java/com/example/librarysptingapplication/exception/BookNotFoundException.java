package com.example.librarysptingapplication.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException() {
        super("Book not found.");
    }
}
