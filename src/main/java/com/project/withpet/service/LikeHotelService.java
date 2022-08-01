package com.project.withpet.service;

import com.project.withpet.domain.Like;
import com.project.withpet.domain.LikeHotel;
import com.project.withpet.repository.LikeHotel.LikeHotelRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class LikeHotelService {
    private final LikeHotelRepository likeHotelRepository;

    public LikeHotelService(LikeHotelRepository likeHotelRepository) {
        this.likeHotelRepository = likeHotelRepository;
    }

    public Long getLikeCount(Long shopId) {
        return likeHotelRepository.findCountByShopId(shopId);
    }

    public int isLiked(Long shopId, String userId) {
        try {
            validate(shopId, userId);
            return 0;
        } catch (IllegalStateException e) {
            return 1;
        }
    }

    public boolean appendLike(Long shopId, String userId) {
        try {
            validate(shopId, userId);
            likeHotelRepository.save(new LikeHotel(shopId, userId));
            return true;
        } catch (IllegalStateException e) {
            deleteLike(likeHotelRepository.findByShopIdandUserId(shopId, userId).get().getId());
            return false;
        }

    }

    public void deleteLike(Long id) {
        likeHotelRepository.delete(new LikeHotel(id));
    }

    public void validate(Long shopId, String userId) {
        Optional<LikeHotel> result = likeHotelRepository.findByShopIdandUserId(shopId, userId);
        result.ifPresent(m -> {
            throw new IllegalStateException("좋아요 눌러져있음");
        });
    }

}
