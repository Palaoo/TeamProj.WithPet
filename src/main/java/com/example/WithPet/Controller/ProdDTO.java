package com.example.WithPet.Controller;

import com.example.WithPet.domain.Product;

public class ProdDTO {
    private Product product;
    private String imgURL;
    private String brand;
    private int likeCount;
    private boolean isLiked;

    public Product getProduct() {
        return product;
    }

    public String getBrand() {
        return brand;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public ProdDTO(Product product, String imgURL,String brand,int likeCount,boolean isLiked) {
        this.product = product;
        this.imgURL = imgURL;
        this.brand = brand;
        this.likeCount =likeCount;
        this.isLiked = isLiked;
    }
}
