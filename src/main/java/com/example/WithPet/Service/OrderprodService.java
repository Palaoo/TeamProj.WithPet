package com.example.WithPet.Service;

import com.example.WithPet.domain.Orderprod;
import com.example.WithPet.repository.Orderprod.OrderprodRepository;

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
