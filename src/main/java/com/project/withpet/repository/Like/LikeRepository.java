package com.project.withpet.repository.Like;

import com.project.withpet.domain.Like;


import java.util.List;
import java.util.Optional;

public interface LikeRepository{
    Like save(Like like);

    void delete(Like like);

    Long findCountByProdId(Long prodId);

    boolean isLike(Long prodId, String userId);

    Optional<Like> findByProdIdandUserId(Long prodId, String userId);


    List<Like> findByUserId(String userId);
}
