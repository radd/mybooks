package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepo;

    @Transactional
    public Author addAuthor(AuthorDTO authorDTO)
            throws Exception {

        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setDateOfBirth(authorDTO.getDateOfBirth());
        author.setPhoto(authorDTO.getPhoto());
        author.setDescription(authorDTO.getDescription());

        return authorRepo.save(author);
    }
}
