package com.project.withpet.service;

import com.project.withpet.domain.Basket;
import com.project.withpet.repository.Basket.BasketRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<Basket> findByUserid(String userid) {
        return basketRepository.findByUserid(userid);
    }

    public Long appendBasket(Long prodId, String userId) throws IllegalStateException {
        validate(userId, prodId);

        return basketRepository.save(new Basket(prodId, userId)).getBasketId();
    }

    private void validate(String userid, Long prodid) {
        basketRepository.findByUseridAndProdid(userid, prodid)
                .ifPresent(b -> {
                    throw new IllegalStateException("이미 장바구니에 존재하는 상품입니다.");
                });
    }

    public void deleteBasket(Long prodId, String userId) {
        Optional<Basket> result = basketRepository.findByUseridAndProdid(userId, prodId);
        basketRepository.delete(result.get());
    }
}
