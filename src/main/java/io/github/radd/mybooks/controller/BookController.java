package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.*;
import io.github.radd.mybooks.domain.dto.AuthorSearchDTO;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.domain.repository.VoteRepository;
import io.github.radd.mybooks.service.impl.BookService;
import io.github.radd.mybooks.service.impl.CategoryService;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AuthUser auth;

    @Autowired
    RatingRepository ratingRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @RequestMapping("/books")
    public String tags(Model model) {

        model.addAttribute("title", "All books");

        Collection<Book> books = bookRepo.findAll();

        model.addAttribute("books", books);


        return "books";
    }

    //TODO move ModelAttribute init to methods - db queries

    @ModelAttribute("authors")
    public List<AuthorSearchDTO> allAuthors() {
        List<Author> authors = authorRepo.findAllByOrderByLastNameAscFirstNameAsc();
        return ObjectMapperUtils.mapAll(authors, AuthorSearchDTO.class);
    }

    @ModelAttribute("cats")
    public List<Category> allCats() {
        return categoryService.getAllCatsForm();
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("title", "Add new book");
        BookDTO bookDTO = new BookDTO();

        model.addAttribute("book", bookDTO);


        return "addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("book") @Valid BookDTO bookDto,
                         BindingResult result, HttpServletRequest req, Model model) {
        Book newBook = null;
        model.addAttribute("added", false);

        if (!result.hasErrors()) {
            newBook = bookService.addBook(bookDto);
        }
        if (newBook != null) {
            model.addAttribute("added", true);
            model.addAttribute("bookPath", newBook.getSlug());
            model.addAttribute("bookTitle", newBook.getTitle());
            model.addAttribute("book", new BookDTO());
        }

        return "addBook";
    }

    @GetMapping("/books/edit/{bookID}")
    public String editBook(@PathVariable String bookID, Model model) {

        if (!auth.isLoggedIn())
            return "404";

        Long Id = Long.parseLong(bookID);
        Book book = bookRepo.findById(Id).orElse(null);

        UserInfo user = auth.getUserInfo();

        if(book == null)
            return "404";

        if(user.getUser().getId() == book.getUser().getId() || user.isAdminOrModerator()) {
            BookDTO editBook = bookService.getBookToEdit(book);

            model.addAttribute("title", "Edit book: " + book.getTitle());
            model.addAttribute("book", editBook);
            model.addAttribute("edit", true);

            return "addBook";
        }

        return "404";
    }

    @PostMapping("/books/edit/{bookID}")
    public String editBook(@PathVariable String bookID, @ModelAttribute("book") @Valid BookDTO bookDto,
                          BindingResult result, HttpServletRequest req, Model model) {
        if (!auth.isLoggedIn())
            return "404";

        Long Id = Long.parseLong(bookID);
        Book book = bookRepo.findById(Id).orElse(null);

        UserInfo user = auth.getUserInfo();

        if(book == null)
            return "404";

        if(user.getUser().getId() == book.getUser().getId() || user.isAdminOrModerator()) {
            Book editBook = null;
            model.addAttribute("edited", false);
            model.addAttribute("edit", true);

            if (!result.hasErrors()) {
                editBook = bookService.editBook(bookDto, book);
            }
            if (editBook != null) {
                model.addAttribute("edited", true);
                model.addAttribute("bookPath", editBook.getSlug());
                model.addAttribute("bookTitle", editBook.getTitle());
                model.addAttribute("book", new BookDTO());
            }

        }

        return "addBook";
    }

    @RequestMapping("/book/{slug}")
    public String getBookBySlug(@PathVariable String slug, Model model) {
        Book book = bookService.getBookBySlug(slug);

        if(book != null) {
            model.addAttribute("title", book.getTitle() + "| Book");
            model.addAttribute("book", book);

            List<Object[]> voteAll = voteRepo.countAllVote(book.getId());
            for(Object[] v : voteAll) {
                model.addAttribute("vote_" + v[0], v[1]);
            }

            if(auth.isLoggedIn()) {
                Rating rating = ratingRepo.findByBookAndUser(book, auth.getUserInfo().getUser());
                model.addAttribute("rating", rating);

                Vote vote = voteRepo.findByBookAndUser(book, auth.getUserInfo().getUser());
                model.addAttribute("vote", vote);
            }

            return "book";
        }

        return "404";
    }

/*    @GetMapping("/reviews/search")
    public String reviewsPage(@PageableDefault(size = 1, sort = "title", direction = Sort.Direction.ASC) Pageable pageable,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String size,
                              Model model) {
        //?page=0&sort=id,DESC

        Page<Review> reviews = reviewRepo.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = reviews.getTotalPages();
        if (page > totalPage)
            return "404";

        model.addAttribute("title", "Recenzje | Strona " + page);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "reviews";
    }*/


}
