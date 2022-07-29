package com.project.withpet.repository.Orderprod;

import com.project.withpet.domain.Orderprod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaRepository extends JpaRepository<Orderprod,Long>, OrderprodRepository {

}
