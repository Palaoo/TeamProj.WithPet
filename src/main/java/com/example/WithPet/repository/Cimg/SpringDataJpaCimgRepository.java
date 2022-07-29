package com.example.WithPet.repository.Cimg;

import com.example.WithPet.domain.Cimg;
import com.example.WithPet.repository.Cimg.CimgRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaCimgRepository extends JpaRepository<Cimg,Long>, CimgRepository {
    @Override
    Optional<Cimg> findByName(String name);
}
