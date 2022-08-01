package com.project.withpet.service;

import com.project.withpet.domain.shoplike;
import com.project.withpet.repository.ShopLike.ShopLikeRepository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ShopLikeService {
    private  final ShopLikeRepository shopLikeRepository;

    public ShopLikeService(ShopLikeRepository shopLikeRepository) {
        this.shopLikeRepository = shopLikeRepository;
    }

    public int saveordelete(String userId, Long shopId) {
        try {
            validate(shopId, userId);

        } catch (IllegalStateException e) {
            shopLikeRepository.delete(userId, shopId);

            return 0; // 좋아요 삭제
        }
        shopLikeRepository.save(new shoplike(userId, shopId));
        return 1; // 좋아요 저장

    }

    public List<shoplike> findByUserId(String userId) {
        return shopLikeRepository.findByUserid(userId);
    }

    public List<shoplike> findByShopId(Long shopId) {
        return shopLikeRepository.findByShopid(shopId);
    }



    public boolean islike(Long shopId, String userId) {
        return !shopLikeRepository.findByUseridAndShopid(userId, shopId).isEmpty();
    }

    private void validate(Long shopId, String userId) {
        shopLikeRepository.findByUseridAndShopid(userId, shopId)
                .ifPresent(sl -> {
                    throw new IllegalStateException("이미 좋아요 누름");
                });
    }
}
