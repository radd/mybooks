package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
