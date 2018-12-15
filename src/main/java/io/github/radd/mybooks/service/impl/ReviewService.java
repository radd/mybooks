package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.ReviewDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Transactional
    public Review addReview(ReviewDTO reviewDTO, Book book) {

        Review review = ObjectMapperUtils.map(reviewDTO, Review.class);
        review.setBook(book);
        review.setSlug(getUniqueSlug(reviewDTO.getTitle()));
        review.setViewCount(0L);
        review.setCreateDate(LocalDateTime.now());

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
}
