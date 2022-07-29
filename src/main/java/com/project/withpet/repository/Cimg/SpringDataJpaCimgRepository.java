package com.project.withpet.repository.Cimg;

import com.project.withpet.domain.Cimg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaCimgRepository extends JpaRepository<Cimg,Long>, CimgRepository {
    @Override
    Optional<Cimg> findByName(String name);
}
