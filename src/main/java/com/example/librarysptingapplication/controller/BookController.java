package com.example.librarysptingapplication.controller;

import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.service.interfaces.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BookController {

    private final IBookService bookService;
    private final ModelMapper modelMapper;

    //Constructors
    public BookController(IBookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    //Find book by id
    @GetMapping("/books/{bookId}")
    public BookDto findBook(@PathVariable Long bookId)
    {
        return bookService.findById(bookId);
    }

    //Find all books
    @GetMapping("/books")
    public Iterable<BookDto> findAll()
    {
        return bookService.findAll();
    }

    //Find all books by author
    @GetMapping("/books/author/{authorId}")
    public Iterable<BookDto> findAllBooks(@PathVariable Long authorId)
    {
        return bookService.findByAuthor(authorId);
    }

    //Find all books by availability
    @GetMapping("/books/available/{isAvailable}")
    public Iterable<BookDto> findByAvailability(@PathVariable boolean isAvailable)
    {
        return bookService.findByAvailability(isAvailable);
    }
    //Find person who borrowed book
    @GetMapping("/books/{bookId}/borrowed")
    public PersonDto findWhoBorrowed(@PathVariable Long bookId)
    {
        return bookService.whoBorrowed(bookId);
    }

    //Count books
    @GetMapping("/books/count")
    public String count()
    {
        return "There are " + bookService.count() + " books in the database.";
    }

    //Add a new book
    @PostMapping("/books")
    public ResponseEntity<String> add(@RequestParam Long authorId, @Valid @RequestBody BookDto bookDto)
    {
        Book book = convertToEntity(bookDto);
        bookService.add(authorId, book);
        return ResponseEntity.ok("New book added.");
    }

    //Update a book
    @PutMapping("/books/{bookId}")
    public ResponseEntity<String> update(@PathVariable Long bookId, @RequestParam(required = false) Long authorId, @Valid @RequestBody BookDto bookDto)
    {
        Book book = convertToEntity(bookDto);
        if(authorId != null)
        {
            bookService.update(bookId, authorId, book);
            return ResponseEntity.ok("Book updated");
        }
        bookService.update(bookId, book);
        return ResponseEntity.ok("Book updated");
    }

    //Delete an author
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> delete(@PathVariable Long bookId)
    {
        bookService.deleteById(bookId);
        return ResponseEntity.ok("Book deleted.");
    }

    //convert Dto to entity
    private Book convertToEntity(BookDto bookDto)
    {
        return modelMapper.map(bookDto, Book.class);
    }
}
