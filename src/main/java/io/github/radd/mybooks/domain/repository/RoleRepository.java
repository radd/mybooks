package io.github.radd.mybooks.domain.repository;

import io.github.radd.mybooks.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
