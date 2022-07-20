package com.project.withpet.repository;

import com.project.withpet.domain.Boardimg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBoardimgRepository extends JpaRepository<Boardimg, Long>, BoardimgRepository {
    @Override
    Optional<Boardimg> findByName(String name);
}
