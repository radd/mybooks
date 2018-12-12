package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.dto.BookDTO;
import io.github.radd.mybooks.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Transactional
    public Book addBook(BookDTO bookDTO)
            throws Exception {

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());

        return bookRepo.save(book);
    }
}
