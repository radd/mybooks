package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.UserDTO;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.domain.repository.UserRepository;
import io.github.radd.mybooks.service.impl.UserService;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/user/{ID}")
    public String user(@PathVariable Long ID, Model model) {

        User user = userRepo.findById(ID).orElse(null);
        if (user == null)
            return "404";

        Pageable reviewsPage = new PageRequest(0,2, Sort.Direction.DESC, "createDate");
        List<Review> reviews = reviewRepo.findAllByUser(user, reviewsPage);

        Pageable ratingsPage = new PageRequest(0,4, Sort.Direction.DESC, "addDate");
        List<Rating> ratings = ratingRepo.findAllByUser(user, ratingsPage);

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("ratings", ratings);

        return "user";
    }


    @GetMapping("/user/{ID}/edit")
    public String editUser(@PathVariable Long ID, Model model) {

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
    public String editUser(@PathVariable Long ID, @ModelAttribute("user") @Valid UserDTO userDTO,
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

    @GetMapping("/user/change-password")
    public String changePassword(Model model) {

        if (!auth.isLoggedIn())
            return "404";

        model.addAttribute("title", "Zmień hasło");

        return "changePassword";
    }

    @PostMapping("/user/change-password")
    public String changePassword(HttpServletRequest req, Model model) {
        if (!auth.isLoggedIn())
            return "404";
        model.addAttribute("edited", false);

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String newPassword2 = req.getParameter("newPassword2");

        if(oldPassword.isEmpty() || newPassword.isEmpty() || newPassword2.isEmpty()) {
            model.addAttribute("errorMsg", "Wypełnij wszystkie pola");
            return "changePassword";
        }

        if(!passwordEncoder.matches(oldPassword, auth.getUserInfo().getUser().getPassword())) {
            model.addAttribute("errorMsg", "Hasło nie jest poprawne");
            return "changePassword";
        }

        if(!newPassword.equals(newPassword2)) {
            model.addAttribute("errorMsg", "Nowe hasła nie są identyczne");
            return "changePassword";
        }

        User user = userService.changePassword(req.getParameter("newPassword"));
        if (user != null) {
            model.addAttribute("edited", true);
            model.addAttribute("userPath", user.getId());
        }

        return "changePassword";
    }


}

