package com.example.librarysptingapplication.controller;

import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.model.Person;
import com.example.librarysptingapplication.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PersonService personService;
    @MockBean
    private ModelMapper modelMapper;

    private Author a1;
    private Author a2;
    private Book b1;
    private Book b3;
    private Book b4;
    private Book b2;
    private PersonDto p1;
    private PersonDto p2;
    private Person p3;
    private Person p4;
    private Person p5;

    //Methods
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void init() {
        a1 = new Author(4L, "test1");
        a2 = new Author(3L, "test2");
        b1 = new Book(5L, "test3", a2, true);
        b2 = new Book(6L, "test4", a1, false);
        b3 = new Book(1L, "test3", a2, false);
        b4 = new Book(2L, "test3", a2, true);
        p1 = new PersonDto(7L, "test1", "0711111111", b1);
        p2 = new PersonDto(8L, "test2", "0722222222");
        p3 = new Person(9L, "test2", "0722222222");
        p4 = new Person(10L, "test2", "0722222222", b4);
        p5 = new Person(10L, "", "", b4);
    }

    @Test
    public void findById() throws Exception
    {
        //GIVEN
        Mockito.when(personService.findById((any()))).thenReturn(p1);
        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/people/" + p1.getPersonId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception
    {
        //GIVEN
        Mockito.when(personService.findAll()).thenReturn(Arrays.asList(p1, p2));

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/people")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void borrowBook() throws Exception
    {
        //GIVEN
        Mockito.doNothing().when(personService).hasBorrowed(p3.getPersonId(), b3.getBookId());

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/people/" + p3.getPersonId() + "/" + b3.getBookId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void returnBook() throws Exception
    {
        //GIVEN
        Mockito.doNothing().when(personService).hasReturned(p4.getPersonId());

        //WHEN
        mvc.perform(MockMvcRequestBuilders.patch("/people/" + p3.getPersonId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addValid() throws Exception
    {
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(p3);
        Mockito.doNothing().when(personService).add(p3);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/people")
                        .content(asJsonString(p3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addFailed() throws Exception
    {
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(p5);
        Mockito.doNothing().when(personService).add(p5);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/people")
                        .content(asJsonString(p5))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putRequest() throws Exception{
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(p4);
        Mockito.doNothing().when(personService).update(9L, p4);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.put("/people/" + 9)
                        .content(asJsonString(p4))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRequest() throws Exception{
        //GIVEN
        Mockito.doNothing().when(personService).deleteById(p3.getPersonId());

        //WHEN
        mvc.perform(MockMvcRequestBuilders.delete("/people/" + p3.getPersonId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

