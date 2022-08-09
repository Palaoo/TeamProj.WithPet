package com.project.withpet.repository.LikeHotel;

import com.project.withpet.domain.Like;
import com.project.withpet.domain.LikeHotel;
import com.project.withpet.domain.ProdReview;

import java.util.List;
import java.util.Optional;

public interface LikeHotelRepository {
    LikeHotel save(LikeHotel likeHotel);

    void delete(LikeHotel likeHotel);

    Long findCountByShopId(Long prodId);

    boolean isLike(Long prodId, String userId);

    Optional<LikeHotel> findByShopIdandUserId(Long prodId, String userId);
}
