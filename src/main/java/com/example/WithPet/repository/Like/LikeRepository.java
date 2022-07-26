package com.example.WithPet.repository.Like;

import com.example.WithPet.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository{
    Like save(Like like);

    void delete(Like like);

    Long findCountByProdId(Long prodId);

    boolean isLike(Long prodId, String userId);
}
