package com.project.withpet.repository.Orderprod;

import com.project.withpet.domain.Orderprod;

import java.util.Optional;

public interface OrderprodRepository {
    Orderprod save(Orderprod orderprod);

    Optional<Orderprod> findByOrderid(Long orderid);
}
