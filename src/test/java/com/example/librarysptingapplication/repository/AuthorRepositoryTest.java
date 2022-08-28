package com.example.librarysptingapplication.repository;

import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    private Author a1, a2;
    private Book b1, b2, b3, b4;


    @BeforeEach
    public void init()
    {
        a1 = new Author("test1");
        a2 = new Author("test2");
        b1 = new Book("test1", a1, true);
        b2 = new Book("test2", a1, false);
        b3 = new Book("test3", a2, true);
        b4 = new Book("test4", a1, false);

        authorRepository.save(a1);
        authorRepository.save(a2);
        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);
        bookRepository.save(b4);
    }

    @Test
    public void findAuthorBooks()
    {
        List<Book> books = authorRepository.findBooks(a1);
        Assertions.assertEquals(Arrays.asList(b1, b2, b4), books);
    }
}
