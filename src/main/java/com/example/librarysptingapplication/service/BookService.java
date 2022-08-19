package com.example.librarysptingapplication.service;

import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.repository.BookRepository;
import com.example.librarysptingapplication.service.interfaces.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    //Constructor
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    //Find book by id
    @Override
    public BookDto findById(Long bookId) {
        Book book = findBookService(bookId);
        return convertToDto(book);
    }

    //Find all books
    @Override
    public List<BookDto> findAll() {
        List<Book> list = bookRepository.findAll();
        if(list.isEmpty())
        {
            //TODO Replace exception
            throw new RuntimeException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    //Find books by author
    @Override
    public List<BookDto> findByAuthor(Long authorId) {
        List<Book> list = bookRepository.findByAuthorId(authorId);
        if(list.isEmpty())
        {
            //TODO Replace exception
            throw new RuntimeException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    //Find books by availability
    @Override
    public List<BookDto> findByAvailability(boolean isBorrowed) {
        List<Book> list = bookRepository.findByBorrowed(isBorrowed);
        if(list.isEmpty())
        {
            //TODO Replace exception
            throw new RuntimeException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    //Count books
    @Override
    public Long count() {
        return bookRepository.count();
    }

    //Add new book
    @Override
    public void addBook(Book newBook) {
        bookRepository.save(newBook);
    }

    //Update a book
    @Override
    public void update(Long bookId, Book updatedInfo) {
        Book book = findBookService(bookId);
        book.setTitle(updatedInfo.getTitle());
        book.setAuthor(updatedInfo.getAuthor());
        bookRepository.save(book);
    }

    //Delete a book
    @Override
    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    //Convert entity to dto
    private BookDto convertToDto(Book book)
    {
        return modelMapper.map(book, BookDto.class);
    }

    //Find book, used only by BookService
    private Book findBookService(Long bookId)
    {
        //TODO Replace exception
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException());
    }
}
