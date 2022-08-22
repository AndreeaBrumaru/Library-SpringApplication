package com.example.librarysptingapplication.repository;

import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthor(Author author);

    @Query("SELECT b FROM Book b WHERE b.isBorrowed = :isBorrowed")
    List<Book> findByBorrowed(boolean isBorrowed);

    @Query("SELECT p FROM Person p WHERE p.bookBorrowed = :book")
    Person whoBorrowed(Book book);
}
