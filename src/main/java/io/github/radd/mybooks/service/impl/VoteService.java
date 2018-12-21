package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.Vote;
import io.github.radd.mybooks.domain.dto.VoteDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.VoteRepository;
import io.github.radd.mybooks.utils.VoteBook;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    AuthUser auth;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    BookService bookService;

    @Transactional
    public Vote addVote(VoteDTO voteDTO) {

        Assert.notNull(voteDTO, "Vote null");
        Assert.notNull(auth.getUserInfo(), "User null");

        VoteBook voteBook = null;
        for(VoteBook v : VoteBook.values()) {
            if(v.is(voteDTO.getVoteType())) {
                voteBook = v;
                break;
            }
        }

        if(voteBook == null)
            return null;

        User user = auth.getUserInfo().getUser();

        //get book
        Book book = bookRepo.findById(voteDTO.getBookID()).orElse(null);

        if(book == null)
            return null;

        //get vote if exists in db
        Vote vote = voteRepo.findByBookAndUser(book, user);

        if(vote == null) { // new vote
            vote = new Vote();
            vote.setBook(book);
            vote.setVoteType(voteBook.getName());
            vote.setUser(user);
        }
        else {
            // if vote type is the same
            if(vote.getVoteType().equals(voteBook.getName()))
                return null;

            //change only vote type
            vote.setVoteType(voteBook.getName());
        }

        //always change date of vote
        vote.setAddDate(LocalDateTime.now());

        if(voteBook.is(VoteBook.REMOVE.getName()))
        {
            voteRepo.delete(vote);
            return vote;
        }

        Vote newVote = voteRepo.save(vote); //add or update vote

        if(newVote == null)
            return null;

        return newVote;
    }

}
