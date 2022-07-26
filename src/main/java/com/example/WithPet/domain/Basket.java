package com.example.WithPet.domain;

import javax.persistence.*;

@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private long basketId;

    @JoinColumn(name = "product_id")
    private Long prodid;

    @JoinColumn(name = "user_id")
    private String userid;


    public long getBasketId() {
        return basketId;
    }

    public void setBasketId(long basketId) {
        this.basketId = basketId;
    }

    public Long getProdId() {
        return prodid;
    }

    public void setProdId(Long prodid) {
        this.prodid = prodid;
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

}
