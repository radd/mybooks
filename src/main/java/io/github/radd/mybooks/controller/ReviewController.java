package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.dto.ReviewDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.service.impl.BookService;
import io.github.radd.mybooks.service.impl.Link;
import io.github.radd.mybooks.service.impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    BookRepository  bookRepo;

    @Autowired
    ReviewService reviewService;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @GetMapping("/review/{bookID}/add")
    public String addReview(@PathVariable String bookID, Model model) {

        Long Id = Long.parseLong(bookID);
        Book book = bookRepo.findById(Id).orElse(null);

        if(book != null) {
            model.addAttribute("title", "Add new review to " + book.getTitle());
            model.addAttribute("review", new ReviewDTO());

            return "addReview";
        }

        return "404";
    }

    @PostMapping("/review/{bookID}/add")
    public String addReview(@PathVariable String bookID, @ModelAttribute("review") @Valid ReviewDTO reviewDTO,
                         BindingResult result, HttpServletRequest req, Model model) {
        Long Id = Long.parseLong(bookID);
        Book book = bookRepo.findById(Id).orElse(null);

        if(book != null) {
            Review newReview = null;
            model.addAttribute("added", false);

            if (!result.hasErrors()) {
                newReview = reviewService.addReview(reviewDTO, book);
            }
            if (newReview != null) {
                model.addAttribute("added", true);
                model.addAttribute("reviewPath", Link.get(newReview));
                model.addAttribute("reviewTitle", newReview.getTitle());
                model.addAttribute("bookID", book.getId());
                model.addAttribute("review", new ReviewDTO());
            }

            return "addReview";
        }

        return "404";
    }

    @GetMapping("/review/{slug}")
    public String reviewPage(@PathVariable String slug, Model model) {

        return "home";
    }


}

