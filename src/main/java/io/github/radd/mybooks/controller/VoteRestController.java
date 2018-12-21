package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.Vote;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.dto.VoteDTO;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.repository.VoteRepository;
import io.github.radd.mybooks.service.impl.RatingService;
import io.github.radd.mybooks.service.impl.VoteService;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote_book")
public class VoteRestController {

    @Autowired
    VoteRepository voteRepo;

    @Autowired
    VoteService voteService;

    @PostMapping("/")
    public Response addVote(@RequestBody VoteDTO voteDTO) {
        Vote vote = voteService.addVote(voteDTO);
        Response response = new Response();
        if(vote != null)
            response.set(ObjectMapperUtils.map(vote, VoteDTO.class)); //TODO Vote Respond

        return response;
    }


}

