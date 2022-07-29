package com.project.withpet.repository;

import com.project.withpet.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository {

    User save(User user);
    Optional<User> findById(String userId);

}
