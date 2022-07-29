package com.project.withpet.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "regid")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "typeid")
    private Shoptype shoptype;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "featlist",
            joinColumns = @JoinColumn(name = "shopid"),
            inverseJoinColumns = @JoinColumn(name = "featid")
    )
    List<Feat> shopFeats;


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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Shoptype getShoptype() {
        return shoptype;
    }

    public void setShoptype(Shoptype shoptype) {
        this.shoptype = shoptype;
    }

    public List<Feat> getShopFeats() {
        return shopFeats;
    }

    public void setShopFeats(List<Feat> shopFeats) {
        this.shopFeats = shopFeats;
    }


}
