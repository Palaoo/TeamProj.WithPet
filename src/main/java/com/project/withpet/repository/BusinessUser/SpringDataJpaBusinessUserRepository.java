package com.project.withpet.repository.BusinessUser;

import com.project.withpet.domain.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBusinessUserRepository extends JpaRepository<BusinessUser, Long>, BusinessUserRepository {
    @Override
    Optional<BusinessUser> findByUid(String uid);
}
