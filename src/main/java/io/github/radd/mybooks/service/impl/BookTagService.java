package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookTagService {

    @Autowired
    private BookTagRepository bookTagRepo;

    @Transactional
    public BookTag addTag(BookTagDTO bookTagDTO) {

        BookTag tag = new BookTag();
        tag.setName(prepareTagName(bookTagDTO.getName()));
        tag.setSlug(getUniqueSlug(bookTagDTO.getName()));
        tag.setBookCount(0L);

        return bookTagRepo.save(tag);
    }

    @Transactional
    public Collection<BookTag> saveAndGetTagsFromString(String data) {
        List<BookTag> allTags = new ArrayList<>();

        if(data != null) {
            //get all names of tags
            List<String> tagNames = Arrays.stream(data.split(","))
                    .filter(s -> (!s.equals("")))
                    .map(this::prepareTagName)
                    .collect(Collectors.toList());

            //get exists tags
            allTags = bookTagRepo.findByNameIn(tagNames);

            //existTags to string nam
            List<String> existTags = allTags.stream()
                    .map(BookTag::getName)
                    .collect(Collectors.toList());

            // remove exists tags(by name)
            tagNames.removeAll(existTags);

            //add tags which not exist
            allTags.addAll(bookTagRepo.saveAll(prepareTagsByName(tagNames)));
        }

        return allTags;
    }

    private List<BookTag> prepareTagsByName(Collection<String> tags) {
        return tags.stream().map(s -> new BookTag(s, getUniqueSlug(s))).collect(Collectors.toList());
    }

    private String prepareTagName(String data) {
        return data.replaceAll(" ", "").trim();
    }

    private String getUniqueSlug(String text) {
        String slug = WebUtils.Slug.makeTagSlug(text);

        slug = !slug.equals("") ? slug : "tag";

        BookTag book = bookTagRepo.findBySlug(slug);

        String tempSlug = slug;
        int suffix = 2;
        while(book != null) {
            slug = tempSlug + "-" + suffix;
            book = bookTagRepo.findBySlug(slug);
            suffix++;
        }

        return slug;
    }

    @Transactional
    public void incrementBookCount(Collection<BookTag> tags) {
        for(BookTag bt : tags) {
            bt.setBookCount(bt.getBookCount()+1);
        }
        bookTagRepo.saveAll(tags);
    }

    public String getTagsFromColl(Collection<BookTag> tags) {
        return tags.stream()
                .map(a -> a.getName())
                .collect(Collectors.joining (","));

    }

}
