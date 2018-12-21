package io.github.radd.mybooks.controller;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Rating;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.AuthorSearchDTO;
import io.github.radd.mybooks.domain.dto.RatingDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.RatingRepository;
import io.github.radd.mybooks.service.impl.AuthorService;
import io.github.radd.mybooks.service.impl.RatingService;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import io.github.radd.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingRestController {

    @Autowired
    RatingRepository ratingRepo;

    @Autowired
    RatingService ratingService;

    @PostMapping("/add")
    public Response addRating(@RequestBody RatingDTO ratingDTO) {
        Rating rating = ratingService.addRating(ratingDTO);
        Response response = new Response();
        if(rating != null)
            response.set(ObjectMapperUtils.map(rating, RatingDTO.class));

        return response;
    }


}

