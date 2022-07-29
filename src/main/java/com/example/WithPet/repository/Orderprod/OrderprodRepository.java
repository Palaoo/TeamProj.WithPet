package com.example.WithPet.repository.Orderprod;

import com.example.WithPet.domain.Orderprod;
import org.aspectj.weaver.ast.Or;

import java.util.Optional;

public interface OrderprodRepository {
    Orderprod save(Orderprod orderprod);

    Optional<Orderprod> findByOrderid(Long orderid);
}
