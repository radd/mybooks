package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.service.impl.AuthorService;
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

    @GetMapping("/authors")
    public String authors(@PageableDefault(size = 1, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable,
                          @RequestParam(required = false) String sort,
                          @RequestParam(required = false) String size,
                          Model model) {
        //?page=0&sort=id,DESC

        Page<Author> authors = authorRepo.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = authors.getTotalPages();
        if (page > totalPage)
            return "404";

        model.addAttribute("title", "Autorzy | Strona " + page);
        model.addAttribute("authors", authors.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "authors";
    }


    @GetMapping("/author/{slug}")
    public String authorPage(@PathVariable String slug, Model model) {

        Author author = authorRepo.findBySlug(slug);

        if(author == null)
            return "404";

        model.addAttribute("title", author.getDisplayName() + "| Autor");
        model.addAttribute("author", author);
        return "author";
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
            model.addAttribute("authorPath", newAuthor.getSlug());
            model.addAttribute("authorName", newAuthor.getDisplayName());
            model.addAttribute("author", new AuthorDTO());
        }

        return "addAuthor";
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
            model.addAttribute("edit", true);

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
            model.addAttribute("edit", true);

            if (!result.hasErrors()) {
                editAuthor = authorService.editAuthor(authorDTO, author);
            }
            if (editAuthor != null) {
                model.addAttribute("edited", true);
                model.addAttribute("authorPath", editAuthor.getSlug());
                model.addAttribute("authorName", editAuthor.getDisplayName());
                model.addAttribute("author", new AuthorDTO());
            }

        }

        return "addAuthor";
    }

    @GetMapping("/authors/search")
    public String searchAuthors(@PageableDefault(size = 1, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable,
                              @RequestParam(required = false) String searchTerm,
                              Model model) {

        if(searchTerm != null && !searchTerm.isEmpty())
        {
            Page<Author> authors = authorRepo.findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(searchTerm, searchTerm, pageable);
            int page = pageable.getPageNumber() + 1;
            int totalPage = authors.getTotalPages();

            model.addAttribute("title", "Szukaj autora: \"" + searchTerm + "\" | Strona " + page);
            model.addAttribute("authors", authors.getContent());
            model.addAttribute("page", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("search", true);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("resultCount", authors.getTotalElements());
        }
        else {
            model.addAttribute("title", "Szukaj autora");
            model.addAttribute("search", false);

        }

        return "searchAuthors";
    }

}

