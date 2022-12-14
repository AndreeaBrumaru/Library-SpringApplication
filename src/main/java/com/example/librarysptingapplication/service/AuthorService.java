package com.example.librarysptingapplication.service;

import com.example.librarysptingapplication.dto.AuthorDto;
import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.exception.AuthorNotFoundException;
import com.example.librarysptingapplication.exception.NoDataFoundException;
import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.repository.AuthorRepository;
import com.example.librarysptingapplication.service.interfaces.IAuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    //Constructor
    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    //Methods
    @Override
    public AuthorDto findById(Long authorId) {
        Author author = findAuthorService(authorId);
        return convertToDto(author);
    }

    @Override
    public List<AuthorDto> findAll() {
        List<Author> list = authorRepository.findAll();
        if(list.isEmpty())
        {
            throw new NoDataFoundException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findBooks(Long authorId) {
        Author author = findAuthorService(authorId);
        List<Book> books = authorRepository.findBooks(author);
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return authorRepository.count();
    }

    @Override
    public void add(Author newAuthor) {
        authorRepository.save(newAuthor);
    }

    @Override
    public void update(Long authorId, Author updatedInfo) {
        Author author = findAuthorService(authorId);
        author.setName(updatedInfo.getName());
        authorRepository.save(author);
    }

    @Override
    public void deleteById(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    //Convert entity to Dto
    private AuthorDto convertToDto(Author author)
    {
        return modelMapper.map(author, AuthorDto.class);
    }

    private BookDto convertToDto(Book book)
    {
        return modelMapper.map(book, BookDto.class);
    }

    //Find author, used only by AuthorService
    private Author findAuthorService(Long authorId)
    {
        return authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
    }
}
