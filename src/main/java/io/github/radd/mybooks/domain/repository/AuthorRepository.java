package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.firstName LIKE %:name% OR a.lastName LIKE %:name% ORDER BY a.lastName ASC")
    List<Author> searchAuthors(String name);
    //List<Author> findByFirstNameContaining(String name);

    List<Author> findAllByOrderByLastNameAscFirstNameAsc();
}
