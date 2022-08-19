package com.example.librarysptingapplication.service.interfaces;

import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.model.Person;

import java.util.List;

public interface IPersonService {

    PersonDto findById(Long personId);
    List<PersonDto> findAll();
    void hasBorrowed(Person person, Book book);
    void hasReturned(Person person);
    Long count();
    void add(Person newPerson);
    void update(Long personId, Person updatedInfo);
    void deleteById(Long personId);
}
