package com.project.withpet.repository.Prod;

import com.project.withpet.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaProd extends JpaRepository<Product, Long>, ProdRepository {

    @Override
    Optional<Product> findByName(String name);
}
