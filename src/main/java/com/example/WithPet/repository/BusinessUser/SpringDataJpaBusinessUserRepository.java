package com.example.WithPet.repository.BusinessUser;

import com.example.WithPet.domain.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBusinessUserRepository extends JpaRepository<BusinessUser, Long>, BusinessUserRepository {
    @Override
    Optional<BusinessUser> findByUser_Id(String user_id);
}
