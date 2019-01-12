package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.ReviewRepository;
import io.github.radd.mybooks.exception.UserNotFoundException;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class HomeController {

    @Autowired
    BookRepository bookRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @RequestMapping("/")
    public String homePage(Model model) {

        Pageable reviewsPage = new PageRequest(0,3, Sort.Direction.DESC, "createDate");
        Page<Review> reviews = reviewRepo.findAll(reviewsPage);

        Pageable ratingsPage = new PageRequest(0,8, Sort.Direction.DESC, "stars");
        Page<Book> books = bookRepo.findAll(ratingsPage);

        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("books", books.getContent());


        model.addAttribute("title", "Strona główna");

        return "home";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model, HttpServletRequest request) {
        if (error != null) {
            model.addAttribute("errorMsg", "Email or password is invalid");
        }
        else {
            String referrer = request.getHeader("Referer");
            request.getSession().setAttribute("previousURL", referrer);
        }
        return "login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest req, UserNotFoundException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", ex.getMsg());
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/logout")
    public String logoutPage(Model model) {
        return "login";
    }

    @Autowired
    AuthUser auth;

    @RequestMapping("/userpage")
    public String userPage(Model model) {

        if (!auth.isLoggedIn())
            return "foo";

        UserInfo user = auth.getUserInfo();

        StringBuilder sb = new StringBuilder();

        sb.append("UserName:").append(user.getUsername());

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            sb.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    sb.append(", ").append(a.getAuthority());
                }
            }
            sb.append(")");
        }

        sb.append(" new: " + user.getAllUserRoles());

        if(user.isAdmin())
            sb.append("| Admin");


        model.addAttribute("greeting", sb.toString());

        model.addAttribute("title", "Logged");
        return "foo";
    }

    @GetMapping(value = "/loginfailed")
    public String loginerror(Model model) {

        model.addAttribute("error", "true");
        return "login";

    }

    @GetMapping(value = "/access-denied")
    public String accessDenied(Model model) {

        model.addAttribute("greeting", "Access denied");
        return "foo";

    }

    @RequestMapping("/foo")
    public String fooPage(Model model) {

        model.addAttribute("title", "Foo");
        model.addAttribute("greeting", "Foo greeting");
        return "foo";
    }

}
