package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Comment;
import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.repository.CommentRepository;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.rest.CommentRequest;
import io.github.radd.mybooks.domain.rest.PageResponse;
import io.github.radd.mybooks.service.impl.CommentService;
import io.github.radd.mybooks.service.impl.RatingService;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    CommentRepository commentRepo;

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

    @GetMapping("/get/{reviewID}")
    public Response getComments(@PageableDefault(size = 1, sort = "addDate", direction = Sort.Direction.ASC) Pageable pageable,
                                    @RequestParam(required = false) String sort,
                                    @RequestParam(required = false) String size,
                                    @PathVariable Long reviewID,
                                    Model model) {
        Response response = new Response();

        PageResponse pageResponse = commentService.getComments(reviewID, pageable);

        if(pageResponse == null)
            return response;

        pageResponse.setSort(sort);
        pageResponse.setSize(size);

        response.set(pageResponse);

        return response;
    }


}

