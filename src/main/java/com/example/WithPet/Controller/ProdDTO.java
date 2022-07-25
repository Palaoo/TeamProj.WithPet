package com.example.WithPet.Controller;

import com.example.WithPet.domain.Product;

public class ProdDTO {
    private Product product;
    private String imgURL;
    private String brand;

    public Product getProduct() {
        return product;
    }

    public String getBrand() {
        return brand;
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

    public ProdDTO(Product product, String imgURL,String brand) {
        this.product = product;
        this.imgURL = imgURL;
        this.brand = brand;
    }
}
