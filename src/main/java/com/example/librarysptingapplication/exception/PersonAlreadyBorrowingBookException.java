package com.example.librarysptingapplication.exception;

public class PersonAlreadyBorrowingBookException extends RuntimeException{
    public PersonAlreadyBorrowingBookException() {
        super("This person is already borrowing a book.");
    }
}
