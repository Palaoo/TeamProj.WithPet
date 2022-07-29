package com.example.WithPet.domain;

import javax.persistence.*;

@Entity
public class BusinessUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long bid;
    @JoinColumn(name="user_id")
    private String uid;
    @Column
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String mobile) {
        this.brand = mobile;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
