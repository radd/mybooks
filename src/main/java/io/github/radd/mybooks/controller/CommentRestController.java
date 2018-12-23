package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Comment;
import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.rest.CommentRequest;
import io.github.radd.mybooks.service.impl.CommentService;
import io.github.radd.mybooks.service.impl.RatingService;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    RatingRepository ratingRepo;

    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    public Response addComment(@RequestBody CommentRequest commentReq) {
        Comment comment = commentService.addComment(commentReq);
        Response response = new Response();
        if(comment != null)
            response.setStatus(Response.DONE);

        return response;
    }


}

