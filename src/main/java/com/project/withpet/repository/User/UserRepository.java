package com.project.withpet.repository.User;

import com.project.withpet.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    List<User> findAll();

    void deleteByUserid(String id);
}
