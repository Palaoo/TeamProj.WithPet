package com.project.withpet.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopid;
    @Column
    private String name;
    @Column
    private String intro;
    @Column
    private String hour;
    @Column
    private String info;
    @Column
    private String address;
    @Column
    private String tel;

    @JoinColumn(name = "region_regid")
    private Long regid;

    @JoinColumn(name = "shoptype_typeid")
    private Long typeid;

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    @Builder
    private Shop(Long shopid, String name, String intro, String hour,
                 String info, String address, String tel, Long regid,
                 Long typeid){
        this.shopid = shopid;
        this.name = name;
        this.intro = intro;
        this.hour = hour;
        this.info = info;
        this.address = address;
        this.tel = tel;
        this.regid = regid;
        this.typeid = typeid;
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

    public Long getRegid() {
        return regid;
    }

    public void setRegid(Long regid) {
        this.regid = regid;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

}
