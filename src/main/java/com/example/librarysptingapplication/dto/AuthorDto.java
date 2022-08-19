package com.example.librarysptingapplication.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AuthorDto {
    private Long authorId;
    @NotBlank(message = "Name is mandatory.")
    private String name;

    //Constructors
    public AuthorDto() {
    }

    public AuthorDto(Long authorId, String name) {
        this.authorId = authorId;
        this.name = name;
    }

    //getters and setters
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorDto authorDto = (AuthorDto) o;

        if (!Objects.equals(authorId, authorDto.authorId)) return false;
        return Objects.equals(name, authorDto.name);
    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    //toString
    @Override
    public String toString() {
        return "AuthorDto{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                '}';
    }
}
