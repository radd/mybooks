package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL OR c.parent = 0 ORDER BY c.name ASC")
    List<Category> findFirstParents();
}
