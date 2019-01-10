package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.Author;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.AuthorDTO;
import io.github.radd.mybooks.domain.dto.UserDTO;
import io.github.radd.mybooks.domain.repository.AuthorRepository;
import io.github.radd.mybooks.domain.repository.UserRepository;
import io.github.radd.mybooks.utils.WebUtils;
import io.github.radd.mybooks.utils.auth.AuthUser;
import io.github.radd.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    AuthUser auth;

    public UserDTO getUserToEdit(User user) {
        UserDTO userDTO =  ObjectMapperUtils.map(user, UserDTO.class);
        return userDTO;
    }

    @Transactional
    public User editUser(UserDTO userDTO, User user) {

        Assert.notNull(userDTO, "User null");
        Assert.notNull(user, "User null");
        Assert.notNull(auth.getUserInfo(), "User null");

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDescription(userDTO.getDescription());
        user.setDisplayName(userDTO.getDisplayName());

        User editUser = userRepo.save(user);

        return editUser;
    }
}
