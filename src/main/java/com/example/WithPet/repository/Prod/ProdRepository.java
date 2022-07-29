package com.example.WithPet.repository.Prod;

import com.example.WithPet.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProdRepository {
    Product save(Product prod);
    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);
}
