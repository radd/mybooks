package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.UserSignUpDTO;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.service.impl.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @RequestMapping("/author")
    public String author(Model model) {

        model.addAttribute("title", "Author");
        model.addAttribute("greeting", "author");
        System.out.println(servletContextPath);
        return "home";
    }

    @RequestMapping("/authors")
    public String authors(Model model) {

        model.addAttribute("title", "All authors");

        return "authors";
    }

    @GetMapping("/author/add")
    public String authorAddPage(Model model) {
        model.addAttribute("title", "Add new author");
        model.addAttribute("author", new AuthorDTO());


        return "addAuthor";
    }

    @PostMapping("/author/add")
    public String signUp(@ModelAttribute("author") @Valid AuthorDTO authorDto,
                         BindingResult result, HttpServletRequest req, Model model) {
        Author newAuthor = null;
        model.addAttribute("added", false);

        if (!result.hasErrors()) {
            newAuthor = addAuthor(authorDto);
        }
        if (newAuthor != null) {
            model.addAttribute("added", true);
            model.addAttribute("authorPath", Link.get(newAuthor));
            model.addAttribute("authorName", newAuthor.getDisplayName());
            model.addAttribute("author", new AuthorDTO());
        }

        return "addAuthor";
    }

    private Author addAuthor(AuthorDTO authorDto) {
        Author author = null;
        try {
            author = authorService.addAuthor(authorDto);
        } catch (Exception e) {
            return null;
        }
        return author;
    }


    @GetMapping("/author/{author}")
    public String authorPage(@PathVariable String author, Model model) {

        model.addAttribute("title", "another autor");
        model.addAttribute("greeting", " another author " + author);
        return "home";
    }


}

