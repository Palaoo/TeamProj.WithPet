package com.project.withpet.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regid;

    @Column
    private String regname;

//    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
//    private List<Shop> shops = new ArrayList<>();
//
//    public Region(String regname){
//        this.regname = regname;
//    }
//
//
//
//    public void addShop(Shop shop){
//        shops.add(shop);
//        shop.setRegion(this);
//    }
//
//    public List<Shop> getShops() {
//        return shops;
//    }
//
//    public void setShops(List<Shop> shops) {
//        this.shops = shops;
//    }

    public Long getRegid() {
        return regid;
    }

    public void setRegid(Long regid) {
        this.regid = regid;
    }

    public String getRegname() {
        return regname;
    }

    public void setRegname(String regname) {
        this.regname = regname;
    }
}
