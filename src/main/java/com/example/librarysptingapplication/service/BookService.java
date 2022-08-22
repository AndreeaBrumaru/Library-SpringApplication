package com.example.librarysptingapplication.service;

import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.dto.PersonDto;
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
        //TODO Replace exception
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new RuntimeException());
        List<Book> list = bookRepository.findByAuthor(author);
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
        //TODO custom error if there are no books for either scenario
        List<Book> list = bookRepository.findByBorrowed(isBorrowed);
        if(list.isEmpty())
        {
            //TODO Replace exception
            throw new RuntimeException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PersonDto whoBorrowed(Long bookId) {
        //TODO If no one borrowed, send error
        Book book = findBookService(bookId);
        Person person = bookRepository.whoBorrowed(book);
        return convertToDto(person);
    }

    //Count books
    @Override
    public Long count() {
        return bookRepository.count();
    }

    //Add new book
    @Override
    public void add(Long authorId, Book newBook) {
        //TODO Replace exception
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException());
        newBook.setAuthor(author);
        newBook.setBorrowed(false);
        bookRepository.save(newBook);
    }

    //Update a book
    @Override
    public void update(Long bookId, Book updatedInfo) {
        Book book = findBookService(bookId);
        book.setTitle(updatedInfo.getTitle());
        bookRepository.save(book);
    }

    @Override
    public void update(Long bookId, Long authorId, Book updatedInfo) {
        //TODO Replace exception
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException());
        Book book = findBookService(bookId);
        book.setTitle(updatedInfo.getTitle());
        book.setAuthor(author);
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
    private PersonDto convertToDto(Person person)
    {
        return modelMapper.map(person, PersonDto.class);
    }

    //Find book, used only by BookService
    private Book findBookService(Long bookId)
    {
        //TODO Replace exception
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException());
    }
}
