package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.*;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.CommentRepository;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.domain.rest.CommentRequest;
import io.github.radd.mybooks.domain.rest.CommentResponse;
import io.github.radd.mybooks.domain.rest.PageResponse;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    AuthUser auth;

    @Autowired
    ReviewRepository reviewRepo;


    @Transactional
    public Comment addComment(CommentRequest commentReq) {

        Assert.notNull(commentReq, "Comment null");
        Assert.notNull(auth.getUserInfo(), "User null");

        if(commentReq.getContent().equals(""))
            return null;

        User user = auth.getUserInfo().getUser();

        //get review
        Review review = reviewRepo.findById(commentReq.getReviewID()).orElse(null);

        if(review == null)
            return null;

        Comment comment = new Comment();
        comment.setContent(commentReq.getContent());
        comment.setReview(review);
        comment.setUser(user);
        comment.setAddDate(LocalDateTime.now());

        return commentRepo.save(comment);
    }

    public PageResponse getComments(Long reviewID, Pageable pageable) {

        Review review = reviewRepo.findById(reviewID).orElse(null);

        if(review == null)
            return null;

        Page<Comment> comments = commentRepo.findAllByReview(review, pageable);

        int page = pageable.getPageNumber() + 1;
        int totalPage = comments.getTotalPages();
        if (page > totalPage)
            return null;

        PageResponse<CommentResponse> pageResponse= new PageResponse<>();
        pageResponse.setContent(commentToResponse(comments.getContent()));
        pageResponse.setCount(comments.getNumberOfElements());
        pageResponse.setPage(page);
        pageResponse.setTotalPage(totalPage);
        pageResponse.setPerPage(comments.getSize());

        return pageResponse;
    }

    private Collection<CommentResponse> commentToResponse(List<Comment> comments) {

        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment : comments) {
            commentResponses.add(commentToResponse(comment));
        }

        return commentResponses;

    }

    private CommentResponse commentToResponse(Comment comment) {
        CommentResponse res = new CommentResponse();
        res.setId(comment.getId());
        res.setContent(comment.getContent());
        res.setAddDate(comment.getDate());
        res.setReviewID(comment.getReview().getId());
        res.setUserID(comment.getUser().getId());
        res.setUserName(comment.getUser().getDisplayName());

        return res;
    }

}
