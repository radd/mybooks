package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookTagService {

    @Autowired
    private BookTagRepository bookTagRepo;

    @Transactional
    public BookTag addTag(BookTagDTO bookTagDTO)
            throws Exception {

        BookTag tag = new BookTag();
        tag.setName(bookTagDTO.getName());
        tag.setBookCount(0L);

        return bookTagRepo.save(tag);
    }
}
