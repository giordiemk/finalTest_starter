package com.giordanobrunomichela.final_test.repository;

import com.giordanobrunomichela.final_test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    List<User> findByDeletedFalse();
}
