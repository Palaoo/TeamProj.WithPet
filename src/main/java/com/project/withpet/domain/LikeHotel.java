package com.project.withpet.domain;

import javax.persistence.*;

@Entity
@Table(name = "like_hotel")
public class LikeHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "shop_shopid")
    @Column(name = "shop_id")
    private Long shopId;

    @JoinColumn(name = "user_userid")
    @Column(name = "user_id")
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public LikeHotel(Long id) {
        this.id = id;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LikeHotel(Long shopId, String userId) {
        this.shopId = shopId;
        this.userId = userId;
    }

    public LikeHotel() {
    }
}
