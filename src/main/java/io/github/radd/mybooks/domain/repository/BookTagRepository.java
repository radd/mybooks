package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {
    List<BookTag> findByNameIn(List<String> names);
}
