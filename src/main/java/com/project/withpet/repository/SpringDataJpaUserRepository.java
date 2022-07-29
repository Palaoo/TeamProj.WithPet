package com.project.withpet.repository;

import com.project.withpet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaUserRepository extends JpaRepository<User, String>, UserRepository {


}
