package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {

}
