package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.*;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.UserDTO;
import io.github.radd.mybooks.domain.dto.UserSignUpDTO;
import io.github.radd.mybooks.domain.repository.*;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.service.impl.UserService;
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
    UserService userService;

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


    @GetMapping("/user/{ID}/edit")
    public String editBook(@PathVariable Long ID, Model model) {

        if (!auth.isLoggedIn())
            return "404";

        if(auth.getUserInfo().getUser().getId() != ID)
            if(!auth.getUserInfo().isAdminOrModerator())
                return "404";

        User user = userRepo.findById(ID).orElse(null);

        if(user == null)
            return "404";

        UserDTO editUser = userService.getUserToEdit(user);

        model.addAttribute("title", "Edit user: " + user.getEmail());
        model.addAttribute("user", editUser);
        model.addAttribute("userName", user.getEmail());

        return "editUser";
    }

    @PostMapping("/user/{ID}/edit")
    public String editBook(@PathVariable Long ID, @ModelAttribute("user") @Valid UserDTO userDTO,
                           BindingResult result, HttpServletRequest req, Model model) {
        if (!auth.isLoggedIn())
            return "404";

        if(auth.getUserInfo().getUser().getId() != ID)
            if(!auth.getUserInfo().isAdminOrModerator())
                return "404";

        User user = userRepo.findById(ID).orElse(null);

        if(user == null)
            return "404";

        User editUser = null;
        model.addAttribute("edited", false);

        if (!result.hasErrors()) {
            editUser = userService.editUser(userDTO, user);
        }
        if (editUser != null) {
            model.addAttribute("edited", true);
            model.addAttribute("userPath", editUser.getId());
            model.addAttribute("userName", editUser.getEmail());
            model.addAttribute("user", new UserDTO());
        }

        return "editUser";
    }


}

