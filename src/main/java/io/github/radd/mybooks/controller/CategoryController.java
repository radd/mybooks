package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Category;
import io.github.radd.mybooks.domain.dto.CategoryDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.CategoryRepository;
import io.github.radd.mybooks.service.impl.CategoryService;
import io.github.radd.mybooks.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    BookRepository bookRepo;

    @RequestMapping("/cats")
    public String tags(Model model) {

        model.addAttribute("title", "All categories");

        return "cats";
    }

    @ModelAttribute("cats")
    public List<Category> allCats() {
        return categoryService.getAllCatsForm();
    }

    @GetMapping("/cat/add")
    public String authorAddPage(Model model) {
        model.addAttribute("title", "Add new category");
        model.addAttribute("cat", new CategoryDTO());

        return "addCat";
    }

    @PostMapping("/cat/add")
    public String signUp(@ModelAttribute("cat") @Valid CategoryDTO categoryDTO,
                         BindingResult result, HttpServletRequest req, Model model) {
        Category category = null;
        model.addAttribute("added", false);

        if (!result.hasErrors()) {
            category = categoryService.addCategory(categoryDTO);
        }
        if (category != null) {
            model.addAttribute("added", true);
            model.addAttribute("catPath", category.getSlug());
            model.addAttribute("catName", category.getName());
            model.addAttribute("cat", new CategoryDTO());
        }
        System.out.println("check3");
        return "addCat";
    }

    @GetMapping("/cat/edit")
    public String editPage(Model model) {
        model.addAttribute("title", "Edit new category");
        model.addAttribute("cat", new CategoryDTO());

        return "addCat";
    }

    @GetMapping("/cat/{slug}")
    public String editPage(@PathVariable String slug, Model model) {
        Category cat = categoryRepo.findBySlug(slug);

        if(cat == null)
            return "404";

        Collection<Book> books = bookRepo.findAllByCategory(cat);


        model.addAttribute("title", cat.getName() + "| Kategoria");
        model.addAttribute("cat", cat);
        model.addAttribute("books", books);
        return "cat";



    }

}

