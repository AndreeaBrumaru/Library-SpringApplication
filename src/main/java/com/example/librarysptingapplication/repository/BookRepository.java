package com.example.librarysptingapplication.repository;

import com.example.librarysptingapplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long authorId);

    @Query("SELECT b FROM Book b WHERE b.isBorrowed = :isBorrowed")
    List<Book> findByBorrowed(boolean isBorrowed);
}
