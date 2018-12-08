package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.service.impl.BookTagService;
import io.github.radd.mybooks.service.impl.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class BookTagController {

    @Autowired
    BookTagService bookTagService;

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
            model.addAttribute("tagPath", Link.get(newTag));
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

}

