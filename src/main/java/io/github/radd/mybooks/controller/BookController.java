package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.Category;
import io.github.radd.mybooks.domain.dto.AuthorSearchDTO;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.service.impl.BookService;
import io.github.radd.mybooks.service.impl.BookTagService;
import io.github.radd.mybooks.service.impl.CategoryService;
import io.github.radd.mybooks.service.impl.Link;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
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
import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    CategoryService categoryService;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @RequestMapping("/books")
    public String tags(Model model) {

        model.addAttribute("title", "All books");

        return "books";
    }

    @ModelAttribute("authors")
    public List<AuthorSearchDTO> allAuthors() {
        List<Author> authors = authorRepo.findAllByOrderByLastNameAscFirstNameAsc();
        return ObjectMapperUtils.mapAll(authors, AuthorSearchDTO.class);
    }

    @ModelAttribute("cats")
    public List<Category> allCats() {
        return categoryService.getAllCatsForm();
    }

    @GetMapping("/book/add")
    public String authorAddPage(Model model) {
        model.addAttribute("title", "Add new book");
        BookDTO bookDTO = new BookDTO();

        model.addAttribute("book", bookDTO);


        return "addBook";
    }

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute("book") @Valid BookDTO bookDto,
                         BindingResult result, HttpServletRequest req, Model model) {
        Book newBook = null;
        model.addAttribute("added", false);

        if (!result.hasErrors()) {
            newBook = bookService.addBook(bookDto);
        }
        if (newBook != null) {
            model.addAttribute("added", true);
            model.addAttribute("bookPath", Link.get(newBook));
            model.addAttribute("bookTitle", newBook.getTitle());
            model.addAttribute("book", new BookDTO());
        }

        return "addBook";
    }

}
