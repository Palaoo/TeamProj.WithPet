package com.example.WithPet.repository.Prod;

import com.example.WithPet.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface SpringDataJpaProd extends JpaRepository<Product, Long>, ProdRepository {

    @Override
    Optional<Product> findByName(String name);
}
