package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByBookAndUser(Book book, User user);

/*    @Query("SELECT v.voteType, COUNT(v) as count FROM vote v WHERE book = :book GROUP BY voteType")*/
    @Query(
            value="select vote_type, COUNT(*) as count FROM vote WHERE book_id = :bookID GROUP BY vote_type ",
            nativeQuery = true
    )
    List<Object[]> countAllVote(Long bookID);
}
