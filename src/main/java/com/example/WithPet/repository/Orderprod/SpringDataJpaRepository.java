package com.example.WithPet.repository.Orderprod;

import com.example.WithPet.domain.Orderprod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaRepository extends JpaRepository<Orderprod,Long>, OrderprodRepository {

}
