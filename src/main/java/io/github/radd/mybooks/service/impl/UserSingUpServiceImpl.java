package io.github.radd.mybooks.service.impl;

import com.sun.javaws.exceptions.InvalidArgumentException;
import io.github.radd.mybooks.domain.Role;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.UserSignUpDTO;
import io.github.radd.mybooks.domain.repository.RoleRepository;
import io.github.radd.mybooks.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class UserSingUpServiceImpl {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public User registerNewUserAccount(UserSignUpDTO accountDto)
            throws Exception {

        if (emailExist(accountDto.getEmail())) {
            throw new Exception(
                    "There is an account with that email address");
        }
        User user = new User();
        user.setName(accountDto.getName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(userRole));

        return repository.save(user);
    }
    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
