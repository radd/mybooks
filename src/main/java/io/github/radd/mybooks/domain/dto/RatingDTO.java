package io.github.radd.mybooks.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RatingDTO {

    @NotNull
    @NotEmpty
    private Integer stars;

    @NotNull
    @NotEmpty
    private Long bookID;


    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }
}