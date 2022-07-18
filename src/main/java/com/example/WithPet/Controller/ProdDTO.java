package com.example.WithPet.Controller;

import com.example.WithPet.domain.Product;

public class ProdDTO {
    private Product product;
    private String imgURL;

    public Product getProduct() {
        return product;
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

    public ProdDTO(Product product, String imgURL) {
        this.product = product;
        this.imgURL = imgURL;
    }
}
