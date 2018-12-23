package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.*;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.CommentRepository;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.domain.rest.CommentRequest;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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

}
