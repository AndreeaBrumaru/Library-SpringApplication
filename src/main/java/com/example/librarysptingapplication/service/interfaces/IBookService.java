package com.example.librarysptingapplication.service.interfaces;

import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Book;

import java.util.List;

public interface IBookService {

    BookDto findById(Long bookId);
    List<BookDto> findAll();
    List<BookDto> findByAuthor(Long authorId);
    List<BookDto> findByAvailability(boolean isBorrowed);
    PersonDto whoBorrowed(Long bookId);
    Long count();
    void add(Long authorId, Book newBook);
    void update(Long bookId, Book updatedInfo);
    void update(Long bookId, Long authorId, Book updatedInfo);
    void deleteById(Long bookId);
}
