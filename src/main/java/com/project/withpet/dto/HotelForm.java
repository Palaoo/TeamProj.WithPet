package com.project.withpet.dto;

import com.project.withpet.domain.Feat;
import com.project.withpet.domain.Region;
import com.project.withpet.domain.Shoptype;

import java.util.List;

public class HotelForm {

    private Long shopid;
    private String name;
    private String intro;
    private String hour;
    private String info;
    private String address;
    private String tel;
    private Region region;

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    private Shoptype shoptype;
    private Long price;
    private String avail;
    private Long likeCount;
    private int isLiked;

    private List<Feat> shopFeats;

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Feat> getShopFeats() {
        return shopFeats;
    }

    public void setShopFeats(List<Feat> shopFeats) {
        this.shopFeats = shopFeats;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

}
