package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.dto.ReviewDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepo;

    @Autowired
    AuthUser auth;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    BookService bookService;

    @Transactional
    public Rating addRating(RatingDTO ratingDTO) {

        Assert.notNull(ratingDTO, "Rating null");
        Assert.notNull(auth.getUserInfo(), "User null");

        User user = auth.getUserInfo().getUser();

        //get book
        Book book = bookRepo.findById(ratingDTO.getBookID()).orElse(null);

        if(book == null)
            return null;

        //get rating if exists in db
        Rating rating = ratingRepo.findByBookAndUser(book, user);

        if(rating == null) { // new rating
            rating = new Rating();
            rating.setBook(book);
            rating.setStars(ratingDTO.getStars());
            rating.setUser(user);
        }
        else {
            //change only stars
            rating.setStars(ratingDTO.getStars());
        }

        //always change date of rating
        rating.setAddDate(LocalDateTime.now());

        Rating newRating = ratingRepo.save(rating); //add or update rating

        if(newRating == null)
            return null;

        //
        //change book info

        Long allStars = ratingRepo.getStarsSum(book);
        Integer ratingCount = ratingRepo.getRatingCount(book); //TODO Integer -> Long

        if(allStars < 0 || ratingCount == 0)
            return null;

        //new value of stars
        float stars = (float) allStars / ratingCount;

        book.setStars(stars);
        book.setRatingCount(ratingCount);
        book = bookRepo.save(book);

        if(book == null) //something goes wrong
            return null;

        return newRating;
    }

}
