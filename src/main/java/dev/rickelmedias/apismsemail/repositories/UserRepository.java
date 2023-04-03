package dev.rickelmedias.apismsemail.repositories;

import dev.rickelmedias.apismsemail.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM users u WHERE u.document = :document",
            nativeQuery = true)
    User findUserByDocument(String document);
}
