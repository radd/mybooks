package io.github.radd.mybooks.domain.dto;

import io.github.radd.mybooks.domain.Author;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public class BookDTO {

    @NotNull
    @NotEmpty
    private String title;

    private String authors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}