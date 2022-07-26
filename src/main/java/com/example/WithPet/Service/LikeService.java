package com.example.WithPet.Service;

import com.example.WithPet.repository.Like.LikeRepository;

public class LikeService {
    LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public int getLikeCount(Long prodId) {
        return likeRepository.findCountByProdId(prodId);
    }

    public boolean isLiked(Long prodId,String userId) {
        return likeRepository.isLike(prodId, userId);
    }
}
