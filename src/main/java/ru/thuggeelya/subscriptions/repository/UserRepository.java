package ru.thuggeelya.subscriptions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thuggeelya.subscriptions.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(final String username);
}