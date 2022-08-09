package com.project.withpet.repository.Boardimg;

import com.project.withpet.domain.Boardimg;
import com.project.withpet.repository.Boardimg.BoardimgRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBoardimgRepository extends JpaRepository<Boardimg, Long>, BoardimgRepository {
    @Override
    Optional<Boardimg> findByName(String name);
}
