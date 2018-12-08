package io.github.radd.mybooks.domain.dto;

import io.github.radd.mybooks.domain.Category;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CategoryDTO {

    @NotNull
    @NotEmpty
    private String name;

    //public Integer parent;

    private Category parent;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Integer getParent() {
//        return parent;
//    }
//
//    public void setParent(Integer parent) {
//        this.parent = parent;
//    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}