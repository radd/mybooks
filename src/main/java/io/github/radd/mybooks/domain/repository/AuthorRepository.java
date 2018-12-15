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

    List<Author> findByIdIn(List<Long> ids);

/*    List<Author> findAll(Iterable<Integer> ids);

    @Query("SELECT l1 FROM Location l1 WHERE l1.node.id IN :ids")
    List<Location> findLocationsByNodeIds(@Param("ids") Set<String> ids);*/

    Author findBySlug(String slug);
}
