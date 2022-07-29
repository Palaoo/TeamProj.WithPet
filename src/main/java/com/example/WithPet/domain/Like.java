package com.example.WithPet.domain;

import javax.persistence.*;

@Entity
@Table(name = "like_table")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private String userId;

    @JoinColumn(name = "product_id")
    @Column(name = "prod_id")
    private Long prodId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getProdId() {
        return prodId;
    }

    public Like() {
    }

    public Like(Long id) {
        this.id = id;
    }

    public Like(Long prodId, String userId) {
        this.userId = userId;
        this.prodId = prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }
}
