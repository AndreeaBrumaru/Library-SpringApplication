package com.example.librarysptingapplication.service;

import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.exception.AuthorNotFoundException;
import com.example.librarysptingapplication.exception.BookNotFoundException;
import com.example.librarysptingapplication.exception.NoDataFoundException;
import com.example.librarysptingapplication.exception.PersonNotFoundException;
import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.model.Person;
import com.example.librarysptingapplication.repository.AuthorRepository;
import com.example.librarysptingapplication.repository.BookRepository;
import com.example.librarysptingapplication.service.interfaces.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    //Constructor
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    //Methods
    @Override
    public BookDto findById(Long bookId) {
        Book book = findBookService(bookId);
        return convertToDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> list = bookRepository.findAll();
        if(list.isEmpty())
        {
            throw new NoDataFoundException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
        List<Book> list = bookRepository.findByAuthor(author);
        if(list.isEmpty())
        {
            throw new NoDataFoundException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByAvailability(boolean isBorrowed) {
        List<Book> list = bookRepository.findByBorrowed(isBorrowed);
        if(list.isEmpty())
        {
            throw new NoDataFoundException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PersonDto whoBorrowed(Long bookId) {
        Book book = findBookService(bookId);
        Person person = bookRepository.whoBorrowed(book);
        if(person == null)
        {
            throw new PersonNotFoundException();
        }
        return convertToDto(person);
    }

    @Override
    public Long count() {
        return bookRepository.count();
    }

    @Override
    public void add(Long authorId, Book newBook) {
        Author author = authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
        newBook.setAuthor(author);
        newBook.setBorrowed(false);
        bookRepository.save(newBook);
    }

    @Override
    public void update(Long bookId, Book updatedInfo) {
        Book book = findBookService(bookId);
        book.setTitle(updatedInfo.getTitle());
        bookRepository.save(book);
    }

    @Override
    public void update(Long bookId, Long authorId, Book updatedInfo) {
        Author author = authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
        Book book = findBookService(bookId);
        book.setTitle(updatedInfo.getTitle());
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    //Convert entity to dto
    private BookDto convertToDto(Book book)
    {
        return modelMapper.map(book, BookDto.class);
    }
    private PersonDto convertToDto(Person person)
    {
        return modelMapper.map(person, PersonDto.class);
    }

    //Find book, used only by BookService
    private Book findBookService(Long bookId)
    {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }
}
