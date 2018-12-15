package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepo;

    @Transactional
    public Author addAuthor(AuthorDTO authorDTO) {

        Author author = ObjectMapperUtils.map(authorDTO, Author.class);
        author.setSlug(getUniqueSlug(author.getDisplayName()));

        return authorRepo.save(author);
    }

    private String getUniqueSlug(String text) {
        String slug = WebUtils.makeSlug(text);

        slug = !slug.equals("") ? slug : "author";

        Author book = authorRepo.findBySlug(slug);

        String tempSlug = slug;
        int suffix = 2;
        while(book != null) {
            slug = tempSlug + "-" + suffix;
            book = authorRepo.findBySlug(slug);
            suffix++;
        }

        return slug;
    }

    public Author getAuthorBySlug(String slug) {
        return authorRepo.findBySlug(slug);
    }
}
