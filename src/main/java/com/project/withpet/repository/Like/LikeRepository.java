package com.project.withpet.repository.Like;

import com.project.withpet.domain.Like;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
import java.util.Optional;

public interface LikeRepository{
    Like save(Like like);

    void delete(Like like);

    Long findCountByProdId(Long prodId);

    boolean isLike(Long prodId, String userId);

    Optional<Like> findByProdIdandUserId(Long prodId, String userId);
<<<<<<< HEAD
=======

    List<Like> findByUserId(String userId);
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
