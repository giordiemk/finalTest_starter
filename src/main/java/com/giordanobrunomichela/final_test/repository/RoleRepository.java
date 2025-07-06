package com.giordanobrunomichela.final_test.repository;

import com.giordanobrunomichela.final_test.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
