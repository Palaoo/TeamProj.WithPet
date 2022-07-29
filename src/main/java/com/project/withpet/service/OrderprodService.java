package com.project.withpet.service;

import com.project.withpet.domain.Orderprod;
import com.project.withpet.repository.Orderprod.OrderprodRepository;

public class OrderprodService {
    private final OrderprodRepository orderprodRepository;


    public OrderprodService(OrderprodRepository orderprodRepository) {
        this.orderprodRepository = orderprodRepository;
    }

    public Long save(Orderprod orderprod) {
        return orderprodRepository.save(orderprod).getOrderid();
    }

    public Orderprod findByOrderid(Long orderid) {
        return orderprodRepository.findByOrderid(orderid).get();
    }
}
