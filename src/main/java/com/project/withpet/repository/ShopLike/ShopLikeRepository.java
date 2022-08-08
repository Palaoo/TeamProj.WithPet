package com.project.withpet.repository.ShopLike;

import com.project.withpet.domain.shoplike;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ShopLikeRepository{
    public void save(shoplike shoplike);

    public List<shoplike> findByUserid(String userId);

    public List<shoplike> findByShopid(Long shopId);
    public Optional findByUseridAndShopid(String userId, Long shopId);

    public void delete(String userId,Long shopId);

<<<<<<< HEAD
=======
    Long findCountByShopid(Long shopId);

>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6


}
