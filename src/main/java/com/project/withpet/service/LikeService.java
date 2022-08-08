package com.project.withpet.service;

import com.project.withpet.domain.Like;
import com.project.withpet.repository.Like.LikeRepository;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
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
<<<<<<< HEAD
=======

    public List<Like> likeList(String userId){
        return likeRepository.findByUserId(userId);
    }
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
