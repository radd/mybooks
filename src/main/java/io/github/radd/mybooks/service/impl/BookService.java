package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.*;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.domain.repository.CategoryRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookTagService bookTagService;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    AuthUser auth;

    @Transactional
    public Book addBook(BookDTO bookDTO) {

        Assert.notNull(bookDTO, "Book null");
        Assert.notNull(auth.getUserInfo(), "User null");

        User user = auth.getUserInfo().getUser();

        Book book = ObjectMapperUtils.map(bookDTO, Book.class);
        book.setAuthors(getAuthorsFromString(bookDTO.getAuthors()));
        book.setCreateDate(LocalDateTime.now());
        book.setSlug(getUniqueSlug(bookDTO.getTitle()));
        book.setBookTags(bookTagService.saveAndGetTagsFromString(bookDTO.getTags()));
        book.setViewCount(0L);
        book.setRatingCount(0);
        book.setStars(0.f);
        book.setUser(user);

        Book newBook = bookRepo.save(book);

        if(newBook != null) {

            //TODO move it to categoryService
            Category cat = newBook.getCategory();
            cat.setBookCount(cat.getBookCount()+1);
            categoryRepo.save(cat);

            Collection<BookTag> tags = newBook.getBookTags();
            bookTagService.incrementBookCount(tags);
        }

        return newBook;
    }

    private List<Author> getAuthorsFromString(String data) {
        List<Author> authors = new ArrayList<>();

        if(data != null) {
            List<Long> ids = getIDsFromString(data);
            authors = authorRepo.findByIdIn(ids);
        }

        return authors;
    }

    private List<Long> getIDsFromString(String data) {

        return Arrays.stream(data.split(","))
                .filter(s -> (!s.equals("") && s.chars().allMatch( Character::isDigit )))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private String getUniqueSlug(String text) {
        String slug = WebUtils.Slug.makeSlug(text);

        slug = !slug.equals("") ? slug : "book";

        Book book = bookRepo.findBySlug(slug);

        String tempSlug = slug;
        int suffix = 2;
        while(book != null) {
            slug = tempSlug + "-" + suffix;
            book = bookRepo.findBySlug(slug);
            suffix++;
        }

        return slug;
    }

    public Book getBookBySlug(String slug) {
        return bookRepo.findBySlug(slug);
    }


    public BookDTO getBookToEdit(Book book) {
        BookDTO editBook =  ObjectMapperUtils.map(book, BookDTO.class);
        editBook.setAuthors(getAuthorsFromColl(book.getAuthors()));
        editBook.setTags(bookTagService.getTagsFromColl(book.getBookTags()));
        return editBook;
    }

    private String getAuthorsFromColl(Collection<Author> authors) {
        return authors.stream()
                .map(a -> a.getId().toString())
                .collect(Collectors.joining (",")) + ","; //TODO remove "," from string -> addBook.jsp refactor js function

    }


    @Transactional
    public Book editBook(BookDTO bookDTO, Book book) {

        Assert.notNull(bookDTO, "Book null");
        Assert.notNull(book, "Book null");
        Assert.notNull(auth.getUserInfo(), "User null");

        book.setTitle(bookDTO.getTitle());
        book.setCategory(bookDTO.getCategory());
        book.setDescription(bookDTO.getDescription());
        book.setPublishYear(bookDTO.getPublishYear());
        book.setPages(bookDTO.getPages());
        book.setOriginalTitle(bookDTO.getOriginalTitle());
        book.setCover(bookDTO.getCover());
        book.setAuthors(getAuthorsFromString(bookDTO.getAuthors()));
        book.setBookTags(bookTagService.saveAndGetTagsFromString(bookDTO.getTags()));

        Book editBook = bookRepo.save(book);

        return editBook;
    }
}
