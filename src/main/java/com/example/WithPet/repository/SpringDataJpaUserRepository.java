package com.example.WithPet.repository;

import com.example.WithPet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaUserRepository extends JpaRepository<User,String>, UserRepository {
    @Override
    Optional<User> findByName(String name);
}
