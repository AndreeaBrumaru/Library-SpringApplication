package com.example.librarysptingapplication.controller;

import com.example.librarysptingapplication.dto.BookDto;
import com.example.librarysptingapplication.dto.PersonDto;
import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.model.Book;
import com.example.librarysptingapplication.service.interfaces.IBookService;
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

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private IBookService bookService;
    @MockBean
    private ModelMapper modelMapper;
    private Author a1;
    private Author a2;
    private BookDto b1;
    private BookDto b2;
    private Book b3;
    private Book b4;
    private PersonDto p1;

    //Methods
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void init()
    {
        a1 = new Author(4L, "test1");
        a2 = new Author(3L, "test2");
        b1 = new BookDto(1L, "test1", a1, true);
        b2 = new BookDto(2L, "test2", a1, false);
        b3 = new Book(5L, "test3", a2, true);
        b4 = new Book(6L, "", a1, false);
        p1 = new PersonDto(7L, "test1", "0711111111", b3);
    }

    //Tests findById
    @Test
    public void findById() throws Exception
    {
        //GIVEN
        Mockito.when(bookService.findById(any())).thenReturn(b1);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/books/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests findAll
    @Test
    public void findAll() throws Exception
    {
        //GIVEN
        Mockito.when(bookService.findAll()).thenReturn(Arrays.asList(b1, b2));

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/books/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests findByAuthor
    @Test
    public void findByAuthor() throws Exception
    {
        //GIVEN
        Mockito.when(bookService.findByAuthor(3L)).thenReturn(Arrays.asList(b1, b2));

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/books/author/" + 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests findWhoBorrowed
    @Test
    public void findBorrowedBy() throws Exception
    {
        //GIVEN
        Mockito.when(bookService.whoBorrowed(5L)).thenReturn(p1);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/books/" + 5 + "/borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests adding books
    @Test
    public void addPassed() throws Exception
    {
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(b3);
        Mockito.doNothing().when(bookService).add(a2.getAuthorId(), b3);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/books?authorId=" + 3)
                        .content(asJsonString(b3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addFailed() throws Exception
    {
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(b4);
        Mockito.doNothing().when(bookService).add(a1.getAuthorId(), b4);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/books?authorId=" + 4)
                        .content(asJsonString(b4))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putRequest() throws Exception{
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(b3);
        Mockito.doNothing().when(bookService).update(5L, b3);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.put("/books/" + 5)
                        .content(asJsonString(b3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRequest() throws Exception{
        //GIVEN
        Mockito.doNothing().when(bookService).deleteById(b3.getBookId());

        //WHEN
        mvc.perform(MockMvcRequestBuilders.delete("/books/" + 5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
