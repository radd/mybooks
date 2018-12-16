package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.UserSignUpDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.service.impl.Link;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.user.UserInfo;
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

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    AuthUser auth;

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

    @GetMapping("/authors/add")
    public String authorAddPage(Model model) {
        model.addAttribute("title", "Add new author");
        model.addAttribute("author", new AuthorDTO());


        return "addAuthor";
    }

    @PostMapping("/authors/add")
    public String signUp(@ModelAttribute("author") @Valid AuthorDTO authorDto,
                         BindingResult result, HttpServletRequest req, Model model) {
        Author newAuthor = null;
        model.addAttribute("added", false);

        if (!result.hasErrors()) {
            newAuthor = authorService.addAuthor(authorDto);
        }
        if (newAuthor != null) {
            model.addAttribute("added", true);
            model.addAttribute("authorPath", Link.get(newAuthor));
            model.addAttribute("authorName", newAuthor.getDisplayName());
            model.addAttribute("author", new AuthorDTO());
        }

        return "addAuthor";
    }

    @GetMapping("/author/{author}")
    public String authorPage(@PathVariable String author, Model model) {

        model.addAttribute("title", "another autor");
        model.addAttribute("greeting", " another author " + author);
        return "home";
    }


    @GetMapping("/authors/edit/{authorID}")
    public String editBook(@PathVariable String authorID, Model model) {

        if (!auth.isLoggedIn())
            return "404";

        Long Id = Long.parseLong(authorID);
        Author author = authorRepo.findById(Id).orElse(null);

        UserInfo user = auth.getUserInfo();

        if(author == null)
            return "404";

        if(user.getUser().getId() == author.getUser().getId() || user.isAdminOrModerator()) {
            AuthorDTO editAuthor = authorService.getAuthorToEdit(author);

            model.addAttribute("title", "Edit author: " + author.getDisplayName());
            model.addAttribute("author", editAuthor);

            return "addAuthor";
        }

        return "404";
    }

    @PostMapping("/authors/edit/{authorID}")
    public String editBook(@PathVariable String authorID, @ModelAttribute("author") @Valid AuthorDTO authorDTO,
                           BindingResult result, HttpServletRequest req, Model model) {
        if (!auth.isLoggedIn())
            return "404";

        Long Id = Long.parseLong(authorID);
        Author author = authorRepo.findById(Id).orElse(null);

        UserInfo user = auth.getUserInfo();

        if(author == null)
            return "404";

        if(user.getUser().getId() == author.getUser().getId() || user.isAdminOrModerator()) {
            Author editAuthor = null;
            model.addAttribute("edited", false);

            if (!result.hasErrors()) {
                editAuthor = authorService.editAuthor(authorDTO, author);
            }
            if (editAuthor != null) {
                model.addAttribute("edited", true);
                model.addAttribute("authorPath", Link.get(editAuthor));
                model.addAttribute("authorName", editAuthor.getDisplayName());
                model.addAttribute("author", new AuthorDTO());
            }

        }

        return "addAuthor";
    }

}

