package com.example.librarysptingapplication.exception;

public class BookAlreadyBorrowedException extends RuntimeException{
    public BookAlreadyBorrowedException() {
        super("Book is already borrowed by someone else");
    }
}
