package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.Category;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.BookRepository;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.domain.repository.CategoryRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
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
    private BookTagRepository bookTagRepo;

    @Autowired
    private CategoryRepository categoryRepo;


    @Transactional
    public Book addBook(BookDTO bookDTO) {

        Assert.notNull(bookDTO, "Book null");

        Book book = ObjectMapperUtils.map(bookDTO, Book.class);
        book.setAuthors(getAuthors(bookDTO.getAuthors()));
        book.setCreateDate(LocalDateTime.now());
        book.setSlug(getUniqueSlug(bookDTO.getTitle()));
        book.setBookTags(getTags(bookDTO.getTags()));

        Book newBook = bookRepo.save(book);

        if(newBook != null) {

            Category cat = newBook.getCategory();
            cat.setBookCount(cat.getBookCount()+1);
            categoryRepo.save(cat);

            Collection<BookTag> tags = newBook.getBookTags();
            for(BookTag bt : tags) {
                bt.setBookCount(bt.getBookCount()+1);
            }
            bookTagRepo.saveAll(tags);
        }

        return bookRepo.save(book);
    }

    public List<Author> getAuthors(String data) {
        List<Author> authors = new ArrayList<>();
        //List<Integer> idList = new ArrayList<>();
        if(data != null) {

/*           String[] ids = data.split(",");
            for(String id : ids) {
                if(!id.equals("") && id.chars().allMatch( Character::isDigit )) {
                    idList.add(Integer.parseInt(id));
                }
            }*/

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
        String slug = WebUtils.makeSlug(text);

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

    private List<BookTag> getTags(String data) {
        List<BookTag> allTags = new ArrayList<>();

        if(data != null) {
            List<String> tagNames = Arrays.stream(data.split(","))
                    .filter(s -> (!s.equals("")))
                    .map(s -> prepareTagName(s))
                    .collect(Collectors.toList()); //get all names of tags
            allTags = bookTagRepo.findByNameIn(tagNames); //get exists tags
            List<String> existTags = allTags.stream().map(t -> t.getName()).collect(Collectors.toList()); //existTags to string names
            tagNames.removeAll(existTags); // remove exists tags(by name)
            allTags.addAll(bookTagRepo.saveAll(tagNames.stream().map(s -> new BookTag(s)).collect(Collectors.toList())));
        }

        return allTags;
    }

    private String prepareTagName(String data) {
        return data.replaceAll(" ", "").trim();
    }
}
