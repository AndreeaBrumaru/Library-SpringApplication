package com.example.librarysptingapplication.controller;

import com.example.librarysptingapplication.dto.AuthorDto;
import com.example.librarysptingapplication.model.Author;
import com.example.librarysptingapplication.service.interfaces.IAuthorService;
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

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private IAuthorService authorService;
    @MockBean
    private ModelMapper modelMapper;
    private AuthorDto a1;
    private AuthorDto a2;
    private AuthorDto a3;
    private Author a4;
    private Author a5;

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
        a1 = new AuthorDto(1L, "test1");
        a2 = new AuthorDto(2L, "test2");
        a3 = new AuthorDto(3L, "test3");
        a4 = new Author("test4");
        a5 = new Author("");
    }

    //Tests findById
    @Test
    public void findById() throws Exception
    {
        //GIVEN
        Mockito.when(authorService.findById(any())).thenReturn(a1);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/authors/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests findAll
    @Test
    public void findAll() throws Exception
    {
        //GIVEN
        Mockito.when(authorService.findAll()).thenReturn(Arrays.asList(a1, a2, a3));

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/authors/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests adding books
    @Test
    public void addPassed() throws Exception
    {
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(a4);
        Mockito.doNothing().when(authorService).add(a4);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(asJsonString(a4))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addFailed() throws Exception
    {
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(a5);
        Mockito.doNothing().when(authorService).add(a5);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(asJsonString(a5))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Tests update
    @Test
    public void putRequest() throws Exception{
        //GIVEN
        Mockito.when(modelMapper.map(any(), any())).thenReturn(a4);
        Mockito.doNothing().when(authorService).update(1L, a4);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.put("/authors/" + 1)
                        .content(asJsonString(a4))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Tests delete
    @Test
    public void deleteRequest() throws Exception{
        //GIVEN
        Mockito.doNothing().when(authorService).deleteById(a1.getAuthorId());

        //WHEN
        mvc.perform(MockMvcRequestBuilders.delete("/authors/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
