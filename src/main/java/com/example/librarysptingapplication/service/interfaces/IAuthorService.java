package com.example.librarysptingapplication.service.interfaces;

import com.example.librarysptingapplication.dto.AuthorDto;
import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.model.Author;

import java.util.List;
public interface IAuthorService {

    AuthorDto findById(Long authorId);
    List<AuthorDto> findAll();
    List<BookDto> findBooks(Long authorId);
    Long count();
    void add(Author newAuthor);
    void update(Long authorId, Author updatedInfo);
    void deleteById(Long authorId);
}
