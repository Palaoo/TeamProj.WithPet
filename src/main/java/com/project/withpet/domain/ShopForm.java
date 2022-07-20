package com.project.withpet.domain;

import com.project.withpet.domain.Shop;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ShopForm {

    private Long shopid;
    private String name;
    private String intro;
    private String hour;
    private String info;
    private String address;
    private String tel;
    private String regname;

//    private List<Shop> shops = new ArrayList<>();
    private String typename;

//    public ShopForm(String regname, List<Shop> shops) {
//        this.regname = regname;
//        this.shops = shops;
//    }

//    public List<Shop> getShops() {
//        return shops;
//    }
//
//    public void setShops(List<Shop> shops) {
//        this.shops = shops;
//    }

    //    public ShopForm(Long shopid, String name, String intro, String hour, String info, String address, String tel, String regname, String typename) {
//        this.shopid = shopid;
//        this.name = name;
//        this.intro = intro;
//        this.hour = hour;
//        this.info = info;
//        this.address = address;
//        this.tel = tel;
//        this.regname = regname;
//        this.typename = typename;
//    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRegname() {
        return regname;
    }

    public void setRegname(String regname) {
        this.regname = regname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
