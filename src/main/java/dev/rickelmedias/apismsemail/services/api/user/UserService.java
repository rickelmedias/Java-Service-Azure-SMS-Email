package dev.rickelmedias.apismsemail.services.api.user;

import dev.rickelmedias.apismsemail.entities.User;

public interface UserService {
    User getUserByDocument(String document);
}
