package com.example.WithPet.domain;

import javax.persistence.*;

@Entity
public class Basket {
    @Id
    @JoinColumn(name = "product_id")
    private Long prodid;


    @JoinColumn(name = "user_id")
    private String userid;


    public Long getProdid() {
        return prodid;
    }

    public void setProdid(Long prodid) {
        this.prodid = prodid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
