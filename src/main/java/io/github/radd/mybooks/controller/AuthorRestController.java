package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.dto.AuthorSearchDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    AuthorRepository authorRepo;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @GetMapping("/search/{name}")
    public List<AuthorSearchDTO> search(@PathVariable String name) {
        System.out.println(name);
        List<Author> authors = authorRepo.searchAuthors(name);

        return ObjectMapperUtils.mapAll(authors, AuthorSearchDTO.class);
    }

    @PostMapping("/add")
    public Response addAuthor(@RequestBody AuthorSearchDTO authorSearchDTO) {
        Author author = authorRepo.save(ObjectMapperUtils.map(authorSearchDTO, Author.class));
        Response response = new Response();
        if(author != null)
            response.set(ObjectMapperUtils.map(author, AuthorSearchDTO.class));

        return response;
    }


}

