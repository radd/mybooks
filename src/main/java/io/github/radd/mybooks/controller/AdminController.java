package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.repository.UserRepository;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
public class AdminController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AuthUser auth;

    @GetMapping("admin/users")
    public String reviewsPage(@PageableDefault(size = 1, sort = "email", direction = Sort.Direction.ASC) Pageable pageable,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String size,
                              Model model) {

        Page<User> users = userRepo.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = users.getTotalPages();
        if (page > totalPage)
            return "404";

        model.addAttribute("title", "Lista użytkowników | Strona " + page);
        model.addAttribute("users", users.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "users";
    }



}

