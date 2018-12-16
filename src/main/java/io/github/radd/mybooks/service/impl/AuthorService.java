package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    AuthUser auth;


    @Transactional
    public Author addAuthor(AuthorDTO authorDTO) {

        Assert.notNull(authorDTO, "Author null");
        Assert.notNull(auth.getUserInfo(), "User null");

        User user = auth.getUserInfo().getUser();

        Author author = ObjectMapperUtils.map(authorDTO, Author.class);
        author.setSlug(getUniqueSlug(author.getDisplayName()));
        author.setUser(user);

        return authorRepo.save(author);
    }

    private String getUniqueSlug(String text) {
        String slug = WebUtils.Slug.makeSlug(text);

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

    public AuthorDTO getAuthorToEdit(Author author) {
        AuthorDTO editAuthor =  ObjectMapperUtils.map(author, AuthorDTO.class);
        return editAuthor;
    }

    @Transactional
    public Author editAuthor(AuthorDTO authorDTO, Author author) {

        Assert.notNull(authorDTO, "Author null");
        Assert.notNull(author, "Author null");
        Assert.notNull(auth.getUserInfo(), "User null");

        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setDescription(authorDTO.getDescription());
        author.setDateOfBirth(authorDTO.getDateOfBirth());
        author.setPhoto(authorDTO.getPhoto());

        Author editAuthor = authorRepo.save(author);

        return editAuthor;
    }
}
