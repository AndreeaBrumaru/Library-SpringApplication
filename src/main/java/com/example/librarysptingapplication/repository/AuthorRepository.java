package com.example.librarysptingapplication.repository;

import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findBooks(Author author);
}
