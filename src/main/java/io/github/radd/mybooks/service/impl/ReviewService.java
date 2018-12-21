package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.ReviewDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    AuthUser auth;

    @Transactional
    public Review addReview(ReviewDTO reviewDTO, Book book) {

        Assert.notNull(reviewDTO, "Review null");
        Assert.notNull(book, "Book null");
        Assert.notNull(auth.getUserInfo(), "User null");

        User user = auth.getUserInfo().getUser();

        Review review = ObjectMapperUtils.map(reviewDTO, Review.class);
        review.setBook(book);
        review.setSlug(getUniqueSlug(reviewDTO.getTitle()));
        review.setViewCount(0L);
        review.setCreateDate(LocalDateTime.now());
        review.setUser(user);

        return reviewRepo.save(review);
    }

    private String getUniqueSlug(String text) {
        String slug = WebUtils.Slug.makeSlug(text);

        slug = !slug.equals("") ? slug : "review";

        Review review = reviewRepo.findBySlug(slug);

        String tempSlug = slug;
        int suffix = 2;
        while(review != null) {
            slug = tempSlug + "-" + suffix;
            review = reviewRepo.findBySlug(slug);
            suffix++;
        }

        return slug;
    }

    public Review getReviewBySlug(String slug) {
        return reviewRepo.findBySlug(slug);
    }

    public ReviewDTO getReviewToEdit(Review review) {
        ReviewDTO editReview =  ObjectMapperUtils.map(review, ReviewDTO.class);
        return editReview;
    }

    @Transactional
    public Review editReview(ReviewDTO reviewDTO, Review review) {

        Assert.notNull(reviewDTO, "Review null");
        Assert.notNull(review, "Review null");
        Assert.notNull(auth.getUserInfo(), "User null");

        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());

        Review editReview = reviewRepo.save(review);

        return editReview;
    }

    public void incrementViewCount(Review review) {
        review.setViewCount(review.getViewCount()+1);
        reviewRepo.save(review);
    }
}
