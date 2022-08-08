package com.project.withpet.repository.ShopLike;

import com.project.withpet.domain.shoplike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeShopRepository extends JpaRepository<shoplike, Long> {
<<<<<<< HEAD

=======
    void deleteByShopid(Long shopid);
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
