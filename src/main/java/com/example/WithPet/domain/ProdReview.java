package com.example.WithPet.domain;

import javax.persistence.*;

@Entity
@Table(name = "prod_review")
public class ProdReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @JoinColumn(name = "product_id")
    @Column(name = "prod_id")
    private Long prodId;

    public ProdReview() {
    }

    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private String userId;

    @Column(columnDefinition = "TEXT")
    private String text;

    public ProdReview(Long prodId, String userId, String text, int star) {
        this.prodId = prodId;
        this.userId = userId;
        this.text = text;
        this.star = star;
    }

    private int star;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
