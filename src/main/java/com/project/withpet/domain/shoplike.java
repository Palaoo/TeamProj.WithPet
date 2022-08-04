package com.project.withpet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likeshop")
public class shoplike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeid;

    @JoinColumn(name = "user_userid")
    private String userid;

    @JoinColumn(name = "shop_shopid")
    private Long shopid;

    public Long getLikeid() {
        return likeid;
    }

    public void setLikeid(Long likeid) {
        this.likeid = likeid;
    }

    public shoplike(Long shopid) {
        this.shopid = shopid;
    }

    public shoplike(String userid, Long shopid) {
        this.userid = userid;
        this.shopid = shopid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }
}
