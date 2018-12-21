package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByBookAndUser(Book book, User user);
}
