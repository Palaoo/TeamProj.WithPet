package com.example.WithPet.Controller;

import com.example.WithPet.domain.Product;

public class ProdandCount {
    Product prod;
    int count;

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ProdandCount(Product product, int count) {
        this.prod = product;
        this.count = count;
    }
}
