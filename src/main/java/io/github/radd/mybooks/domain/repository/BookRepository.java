package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBySlug(String slug);
    List<Book> findAllByCategory(Category cat);

    List<Book> findAllByBookTags(Collection<BookTag> bookTags);

    Page<Book> findAllByTitleIgnoreCaseContaining(String title, Pageable pageable);
}
