package com.example.WithPet.Service;

import com.example.WithPet.domain.Like;
import com.example.WithPet.repository.Like.LikeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class LikeService {
    LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Long getLikeCount(Long prodId) {
        return likeRepository.findCountByProdId(prodId);
    }

    public boolean isLiked(Long prodId, String userId) {
        return likeRepository.isLike(prodId, userId);
    }

    public void appendLike(Long prodId, String userId) {
        likeRepository.save(new Like(prodId, userId));
    }

    public void deleteLike(Long prodId, String userId) {
        likeRepository.delete(new Like(prodId,userId));
    }
}
