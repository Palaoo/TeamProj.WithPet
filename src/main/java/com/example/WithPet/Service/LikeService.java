package com.example.WithPet.Service;

import com.example.WithPet.domain.Like;
import com.example.WithPet.repository.Like.LikeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class LikeService {
    LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Long getLikeCount(Long prodId) {
        return likeRepository.findCountByProdId(prodId);
    }

    public int isLiked(Long prodId, String userId) {
        try {
            validate(prodId, userId);
            return 0;
        } catch (IllegalStateException e) {
            return 1;
        }
    }

    public boolean appendLike(Long prodId, String userId) {
        try {
            validate(prodId, userId);
            likeRepository.save(new Like(prodId, userId));
            return true;
        } catch (IllegalStateException e) {
            deleteLike(likeRepository.findByProdIdandUserId(prodId, userId).get().getId());
            return false;
        }

    }

    public void deleteLike(Long id) {
        likeRepository.delete(new Like(id));
    }

    public void validate(Long prodId, String userId) {
        Optional<Like> result = likeRepository.findByProdIdandUserId(prodId, userId);
        result.ifPresent(m -> {
            throw new IllegalStateException("좋아요 눌러져있음");
        });
    }
}
