package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBySlug(String slug);
}
