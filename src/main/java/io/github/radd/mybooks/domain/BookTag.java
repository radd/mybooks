package io.github.radd.mybooks.domain;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "book_tag")
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;
    private String slug;

    @Column(name = "book_count")
    private Long bookCount = 0L;

    @ManyToMany(mappedBy = "bookTags")
    private Collection<Book> books;


    public BookTag() {
    }

    public BookTag(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookCount() {
        return bookCount != null ? bookCount : 0L;
    }

    public void setBookCount(Long bookCount) {
        this.bookCount = bookCount;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
