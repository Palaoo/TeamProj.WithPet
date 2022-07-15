package com.example.WithPet.repository.User;

import com.example.WithPet.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    List<User> findAll();
}
