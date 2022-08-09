package com.project.withpet.dto;

public class BascketDTO {
    private String path;


    private String name;
    private int price;
    private Long prodid;
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getProdid() {
        return prodid;
    }

    public void setProdid(Long prodid) {
        this.prodid = prodid;
    }

    public BascketDTO(Long prodid, String name, int price, String path,String brand) {
        this.path = path;
        this.name = name;
        this.price = price;
        this.prodid = prodid;
        this.brand = brand;
    }
}
