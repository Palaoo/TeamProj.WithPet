package com.project.withpet.service;

import com.project.withpet.repository.Order.OrderRepository;
import com.project.withpet.domain.Ordertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class OrderService {
    @Autowired
    EntityManager em;

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Ordertable save(Ordertable order) {
        return orderRepository.save(order);
    }

    public Ordertable findByUserid(Long userid) {
        return orderRepository.findByUserid(userid).get();
    }

    public List<Ordertable> findByDate(String userid, LocalDateTime start, LocalDateTime end, int paging) {
        String query = "select o from Ordertable o where o.userid = :userid and o.orderdate between :start and :end order by o.orderdate DESC";
        List<Ordertable> findOrder = em.createQuery(query, Ordertable.class).setParameter("userid", userid)
                .setParameter("start", start).setParameter("end", end).setFirstResult(paging * 10).setMaxResults(10).getResultList();
        System.out.printf("From OrderService findByDate(), start: %s, end: %s\n",
                start.toString(), end.toString());
        for (Ordertable ordertable : findOrder) {
            System.out.printf("From OrderService findByDate(), ordertable.userid: %s, ordertable.orderdate:%s\n",
                    ordertable.getUserid(), ordertable.getOrderdate());
        }
        return findOrder;
    }
}
