package com.example.librarysptingapplication.dto;

import com.example.librarysptingapplication.model.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class PersonDto {

    private Long personId;
    @NotBlank(message = "Name is mandatory.")
    private String name;
    @NotBlank(message = "Phone number is mandatory.")
    @Size(min = 10, max = 10, message = "The phone number must be 10 characters long.")
    private String phoneNumber;
    private Book bookBorrowed;

    //Constructors
    public PersonDto(Long personId, String name, String phoneNumber) {
        this.personId = personId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public PersonDto() {
    }

    //Getters and setters
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Book getBookBorrowed() {
        return bookBorrowed;
    }

    public void setBookBorrowed(Book bookBorrowed) {
        this.bookBorrowed = bookBorrowed;
    }

    //Equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDto personDto = (PersonDto) o;

        if (!Objects.equals(personId, personDto.personId)) return false;
        if (!Objects.equals(name, personDto.name)) return false;
        return Objects.equals(phoneNumber, personDto.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    //toString
    @Override
    public String toString() {
        return "PersonDto{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
