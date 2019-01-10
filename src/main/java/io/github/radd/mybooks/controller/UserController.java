package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.*;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.repository.*;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    RatingRepository ratingRepo;


    @Autowired
    AuthUser auth;

    @GetMapping("/user/{ID}")
    public String authors(@PathVariable Long ID, Model model) {

        User user = userRepo.findById(ID).orElse(null);
        if (user == null)
            return "404";

        Pageable reviewsPage = new PageRequest(0,3, Sort.Direction.DESC, "createDate");
        List<Review> reviews = reviewRepo.findAllByUser(user, reviewsPage);

        Pageable ratingsPage = new PageRequest(0,3, Sort.Direction.DESC, "addDate");
        List<Rating> ratings = ratingRepo.findAllByUser(user, ratingsPage);

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("ratings", ratings);


        return "user";
    }

}

