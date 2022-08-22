package com.example.librarysptingapplication.controller;

import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Person;
import com.example.librarysptingapplication.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PersonController {
    private final PersonService personService;
    private final ModelMapper modelMapper;

    //Constructor
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    //Find person by id
    @GetMapping("/people/{personId}")
    public PersonDto findPerson(@PathVariable Long personId)
{
    return personService.findById(personId);
}

    //Find all people
    @GetMapping("/people")
    public Iterable<PersonDto> findAll()
{
    return personService.findAll();
}

    //Borrow book method
    @PostMapping("/people/{personId}/{bookId}")
    public ResponseEntity<String> hasBorrowed(@PathVariable Long personId, @PathVariable Long bookId)
    {
        personService.hasBorrowed(personId, bookId);
        return ResponseEntity.ok("Person has borrowed a book.");
    }

    //Return a book
    @PatchMapping("/people/{personId}")
    public ResponseEntity<String> hasReturned(@PathVariable Long personId)
    {
        personService.hasReturned(personId);
        return ResponseEntity.ok("Person returned a book.");
    }

    //Count people
    @GetMapping("/people/count")
    public String count()
{
    return "There are " + personService.count() + " people in the database.";
}

    //Add a new person
    @PostMapping("/people")
    public ResponseEntity<String> add(@Valid @RequestBody PersonDto personDto)
    {
        Person person = convertToEntity(personDto);
        personService.add(person);
        return ResponseEntity.ok("New person added.");
    }

    //Update a person
    @PutMapping("/people/{personId}")
    public ResponseEntity<String> update(@PathVariable Long personId, @Valid @RequestBody PersonDto personDto)
    {
        Person person = convertToEntity(personDto);
        personService.update(personId, person);
        return ResponseEntity.ok("Person updated");
    }

    //Delete a person
    @DeleteMapping("/people/{personId}")
    public ResponseEntity<String> delete(@PathVariable Long personId)
    {
        personService.deleteById(personId);
        return ResponseEntity.ok("Person deleted.");
    }

    //convert Dto to entity
    private Person convertToEntity(PersonDto personDto)
    {
        return modelMapper.map(personDto, Person.class);
    }
}
