package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Comment;
import io.github.radd.mybooks.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByReview(Review review, Pageable pageable);

}
