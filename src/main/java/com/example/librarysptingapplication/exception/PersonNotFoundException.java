package com.example.librarysptingapplication.exception;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException() {
        super("Person not found.");
    }
}
