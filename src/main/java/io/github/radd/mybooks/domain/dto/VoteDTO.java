package io.github.radd.mybooks.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VoteDTO {

    @NotNull
    @NotEmpty
    private String voteType;

    @NotNull
    @NotEmpty
    private Long bookID;


    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }
}