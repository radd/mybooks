package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.dto.ReviewDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.service.impl.ReviewService;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Autowired
    ReviewRepository reviewRepo;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @Autowired
    AuthUser auth;

    @GetMapping("/reviews/{bookID}/add")
    public String addReview(@PathVariable String bookID, Model model) {

        Long Id = Long.parseLong(bookID);
        Book book = bookRepo.findById(Id).orElse(null);

        if(book != null) {
            model.addAttribute("title", "Add new review to " + book.getTitle());
            model.addAttribute("review", new ReviewDTO());
            model.addAttribute("book", book);

            return "addReview";
        }

        return "404";
    }

    @PostMapping("/reviews/{bookID}/add")
    public String addReview(@PathVariable String bookID, @ModelAttribute("review") @Valid ReviewDTO reviewDTO,
                         BindingResult result, HttpServletRequest req, Model model) {
        Long Id = Long.parseLong(bookID);
        Book book = bookRepo.findById(Id).orElse(null);

        if(book != null) {
            Review newReview = null;
            model.addAttribute("added", false);
            model.addAttribute("book", book);

            if (!result.hasErrors()) {
                newReview = reviewService.addReview(reviewDTO, book);
            }
            if (newReview != null) {
                model.addAttribute("added", true);
                model.addAttribute("reviewPath", newReview.getSlug());
                model.addAttribute("reviewTitle", newReview.getTitle());
                model.addAttribute("bookID", book.getId());
                model.addAttribute("review", new ReviewDTO());
            }

            return "addReview";
        }

        return "404";
    }


    @GetMapping("/reviews/edit/{reviewID}")
    public String editBook(@PathVariable String reviewID, Model model) {

        if (!auth.isLoggedIn())
            return "404";

        Long Id = Long.parseLong(reviewID);
        Review review = reviewRepo.findById(Id).orElse(null);

        UserInfo user = auth.getUserInfo();

        if(review == null)
            return "404";

        if(user.getUser().getId() == review.getUser().getId() || user.isAdminOrModerator()) {
            ReviewDTO editReview = reviewService.getReviewToEdit(review);

            model.addAttribute("title", "Edit review: " + review.getTitle());
            model.addAttribute("review", editReview);
            model.addAttribute("reviewTitle", editReview.getTitle());
            model.addAttribute("edit", true);

            return "addReview";
        }

        return "404";
    }

    @PostMapping("/reviews/edit/{reviewID}")
    public String editBook(@PathVariable String reviewID, @ModelAttribute("review") @Valid ReviewDTO reviewDTO,
                           BindingResult result, HttpServletRequest req, Model model) {
        if (!auth.isLoggedIn())
            return "404";

        Long Id = Long.parseLong(reviewID);
        Review review = reviewRepo.findById(Id).orElse(null);

        UserInfo user = auth.getUserInfo();

        if(review == null)
            return "404";

        if(user.getUser().getId() == review.getUser().getId() || user.isAdminOrModerator()) {
            Review editReview = null;
            model.addAttribute("edited", false);
            model.addAttribute("edit", true);

            if (!result.hasErrors()) {
                editReview = reviewService.editReview(reviewDTO, review);
            }
            if (editReview != null) {
                model.addAttribute("edited", true);
                model.addAttribute("reviewPath", editReview.getSlug());
                model.addAttribute("reviewTitle", editReview.getTitle());
                model.addAttribute("review", new ReviewDTO());
            }

        }

        return "addReview";
    }


    @GetMapping("/review/{slug}")
    public String reviewPage(@PathVariable String slug, Model model) {
        Review review = reviewRepo.findBySlug(slug);

        if(review == null)
            return "404";

        model.addAttribute("title", review.getTitle() + " | Recenzja");
        model.addAttribute("review", review);

        reviewService.incrementViewCount(review);

        return "review";
    }

    @GetMapping("/reviews")
    public String reviewsPage(@PageableDefault(size = 1, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String size,
                              Model model) {
        //?page=0&sort=id,DESC
        Page<Review> reviews = reviewRepo.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = reviews.getTotalPages();
        if (page > totalPage)
            return "404";

        model.addAttribute("title", "Recenzje | Strona " + page);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "reviews";
    }




}

