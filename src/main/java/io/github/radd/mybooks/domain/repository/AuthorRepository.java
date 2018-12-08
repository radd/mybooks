package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
