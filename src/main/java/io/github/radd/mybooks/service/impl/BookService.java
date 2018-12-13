package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Transactional
    public Book addBook(BookDTO bookDTO) {

        Assert.notNull(bookDTO, "Book null");

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthors(getAuthors(bookDTO.getAuthors()));

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
}
