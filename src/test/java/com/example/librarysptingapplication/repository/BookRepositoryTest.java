package com.example.librarysptingapplication.repository;

import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    PersonRepository personRepository;

    private Author a1, a2;
    private Book b1, b2, b3, b4;
    private Person p1, p2;


    @BeforeEach
    public void init()
    {
        a1 = new Author("test1");
        a2 = new Author("test2");
        b1 = new Book("test1", a1, true);
        b2 = new Book("test2", a1, false);
        b3 = new Book("test3", a2, true);
        b4 = new Book("test4", a1, false);
        p1 = new Person("test1", "0711111111", b1);
        p2 = new Person("test2", "0722222222", b3);

        authorRepository.save(a1);
        authorRepository.save(a2);
        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);
        bookRepository.save(b4);
        personRepository.save(p1);
        personRepository.save(p2);
    }

    @Test
    public void findByAuthor()
    {
        List<Book> books = bookRepository.findByAuthor(a1);
        Assertions.assertEquals(Arrays.asList(b1, b2, b4), books);
    }

    @Test
    public void findByBorrowed()
    {
        List<Book> books = bookRepository.findByBorrowed(true);
        Assertions.assertNotNull(books);
    }

    @Test
    public void findByPerson()
    {
        Person person = bookRepository.whoBorrowed(b3);
        Assertions.assertEquals(p2, person);
    }
}
