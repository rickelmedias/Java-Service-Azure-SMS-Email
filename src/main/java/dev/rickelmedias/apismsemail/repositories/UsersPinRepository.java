package dev.rickelmedias.apismsemail.repositories;

import dev.rickelmedias.apismsemail.entities.User;
import dev.rickelmedias.apismsemail.entities.UserPin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersPinRepository extends JpaRepository<UserPin, Long> {
    @Query(
            value = "SELECT * FROM users_pin u WHERE u.user_id = :userId",
            nativeQuery = true)
    UserPin findUserPinByUserId(Long userId);
}
