package com.example.librarysptingapplication.service;

import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.model.Person;
import com.example.librarysptingapplication.repository.PersonRepository;
import com.example.librarysptingapplication.service.interfaces.IPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    //Constructor
    public PersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    //Find person by id
    @Override
    public PersonDto findById(Long personId) {
        Person person = findPersonService(personId);
        return convertToDto(person);
    }

    //Find all people
    @Override
    public List<PersonDto> findAll() {
        List<Person> list = personRepository.findAll();
        if(list.isEmpty())
        {
            //TODO Replace exception
            throw new RuntimeException();
        }
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    //Set that a book was borrowed by someone
    @Override
    public void hasBorrowed(Person person, Book book) {
        //TODO Implement borrowing book method
    }

    //Set that a book was returned
    @Override
    public void hasReturned(Person person) {
        //TODO Implement returning book method
    }

    //Count all people
    @Override
    public Long count() {
        return personRepository.count();
    }

    //Add a new person
    @Override
    public void add(Person newPerson) {
        personRepository.save(newPerson);
    }

    //Update a person
    @Override
    public void update(Long personId, Person updatedInfo) {
        Person person = findPersonService(personId);
        person.setName(updatedInfo.getName());
        person.setPhoneNumber(updatedInfo.getPhoneNumber());
        personRepository.save(person);
    }

    //Delete a person
    @Override
    public void deleteById(Long personId) {
        personRepository.deleteById(personId);
    }

    //Convert entity to dto
    private PersonDto convertToDto(Person person)
    {
        return modelMapper.map(person, PersonDto.class);
    }

    //Find person, used only by PersonService
    private Person findPersonService(Long personId)
    {
        //TODO Replace exception
        return personRepository.findById(personId).orElseThrow(() -> new RuntimeException());
    }
}
