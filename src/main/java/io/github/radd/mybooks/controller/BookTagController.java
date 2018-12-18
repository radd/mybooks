package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.service.impl.BookTagService;
import io.github.radd.mybooks.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;

@Controller
public class BookTagController {

    @Autowired
    BookTagService bookTagService;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    BookTagRepository bookTagRepo;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @RequestMapping("/tags")
    public String tags(Model model) {

        model.addAttribute("title", "All tags");

        return "tags";
    }

    @GetMapping("/tag/add")
    public String authorAddPage(Model model) {
        model.addAttribute("title", "Add new tag");
        model.addAttribute("tag", new BookTagDTO());


        return "addTag";
    }

    @PostMapping("/tag/add")
    public String signUp(@ModelAttribute("tag") @Valid BookTagDTO bookTagDto,
                         BindingResult result, HttpServletRequest req, Model model) {
        BookTag newTag = null;
        model.addAttribute("added", false);

        if (!result.hasErrors()) {
            newTag = addTag(bookTagDto);
        }
        if (newTag != null) {
            model.addAttribute("added", true);
            model.addAttribute("tagPath", newTag.getSlug());
            model.addAttribute("tagName", newTag.getName());
            model.addAttribute("tag", new BookTagDTO());
        }

        return "addTag";
    }

    private BookTag addTag(BookTagDTO bookTagDto) {
        BookTag tag = null;
        try {
            tag = bookTagService.addTag(bookTagDto);
        } catch (Exception e) {
            return null;
        }
        return tag;
    }

    @GetMapping("/tag/{slug}")
    public String editPage(@PathVariable String slug, Model model) {
        BookTag tag = bookTagRepo.findBySlug(slug);

        if(tag == null)
            return "404";

        Collection<Book> books = bookRepo.findAllByBookTags(Arrays.asList(tag));

        model.addAttribute("title", tag.getName() + "| Tag");
        model.addAttribute("tag", tag);
        model.addAttribute("books", books);
        return "tag";



    }

}

