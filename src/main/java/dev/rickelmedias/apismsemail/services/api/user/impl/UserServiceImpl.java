package dev.rickelmedias.apismsemail.services.api.user.impl;

import dev.rickelmedias.apismsemail.entities.User;
import dev.rickelmedias.apismsemail.repositories.UserRepository;
import dev.rickelmedias.apismsemail.services.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByDocument(String document) {
        User user = userRepository.findUserByDocument(document);
        return user;
    }
}
