package com.example.librarysptingapplication.dto;

import com.example.librarysptingapplication.model.Author;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class BookDto {
    private Long bookId;
    @NotBlank(message = "Title must not be blank.")
    private String title;
    private Author author;
    private boolean isBorrowed;

    //Constructors
    public BookDto(Long bookId, String title, boolean isBorrowed) {
        this.bookId = bookId;
        this.title = title;
        this.isBorrowed = isBorrowed;
    }

    public BookDto() {
    }

    //Getters ans setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    //Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookDto bookDto = (BookDto) o;

        if (isBorrowed != bookDto.isBorrowed) return false;
        if (!Objects.equals(bookId, bookDto.bookId)) return false;
        return Objects.equals(title, bookDto.title);
    }

    @Override
    public int hashCode() {
        int result = bookId != null ? bookId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isBorrowed ? 1 : 0);
        return result;
    }

    //toString
    @Override
    public String toString() {
        return "BookDto{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
