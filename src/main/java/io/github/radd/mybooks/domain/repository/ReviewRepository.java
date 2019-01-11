package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Review;
import io.github.radd.mybooks.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findBySlug(String slug);

    List<Review> findAllByUser(User user, Pageable pageable);

    Page<Review> findAllByTitleIgnoreCaseContainingOrContentIgnoreCaseContaining(String title, String content, Pageable pageable);

}
