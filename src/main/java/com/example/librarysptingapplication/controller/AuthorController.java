package com.example.librarysptingapplication.controller;

import com.example.librarysptingapplication.dto.AuthorDto;
import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.service.interfaces.IAuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthorController {

    private final IAuthorService authorService;
    private final ModelMapper modelMapper;

    //Constructors
    public AuthorController(IAuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    //Find author by id
    @GetMapping("/authors/{authorId}")
    public AuthorDto findAuthor(@PathVariable Long authorId)
    {
        return authorService.findById(authorId);
    }

    //Find all authors
    @GetMapping("/authors")
    public Iterable<AuthorDto> findAll()
    {
        return authorService.findAll();
    }

    //Find all books of an author
    @GetMapping("/authors/{authorId}/books")
    public Iterable<BookDto> findAllBooks(@PathVariable Long authorId)
    {
        return authorService.findBooks(authorId);
    }

    //Count authors
    @GetMapping("/authors/count")
    public String count()
    {
        return "There are " + authorService.count() + " authors in the database.";
    }

    //Add a new author
    @PostMapping("/authors")
    public ResponseEntity<String> add(@Valid @RequestBody AuthorDto authorDto)
    {
        Author author = convertToEntity(authorDto);
        authorService.add(author);
        return ResponseEntity.ok("New author added.");
    }

    //Update an author
    @PutMapping("/authors/{authorId}")
    public ResponseEntity<String> update(@PathVariable Long authorId, @Valid @RequestBody AuthorDto authorDto)
    {
        Author author = convertToEntity(authorDto);
        authorService.update(authorId, author);
        return ResponseEntity.ok("Author updated.");
    }

    //Delete an author
    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<String> delete(@PathVariable Long authorId)
    {
        authorService.deleteById(authorId);
        return ResponseEntity.ok("Author deleted.");
    }

    //convert Dto to entity
    private Author convertToEntity(AuthorDto authorDto)
    {
        return modelMapper.map(authorDto, Author.class);
    }
}
