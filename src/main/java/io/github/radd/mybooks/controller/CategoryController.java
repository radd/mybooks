package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.Category;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.dto.CategoryDTO;
import io.github.radd.mybooks.service.impl.BookTagService;
import io.github.radd.mybooks.service.impl.CategoryService;
import io.github.radd.mybooks.service.impl.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

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
            model.addAttribute("catPath", Link.get(category));
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

}

