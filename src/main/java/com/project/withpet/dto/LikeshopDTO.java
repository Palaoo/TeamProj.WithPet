package com.project.withpet.dto;

import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shoplike;

public class LikeshopDTO {

    private Long likeid;
    private Long shopid;
    private String userid;
    shoplike shoplike;
    cafe cafe;

    boolean islike;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public shoplike getShoplike(){
        return shoplike;
    }

    public void setShoplike(shoplike shoplike){
        this.shoplike = shoplike;
    }

    public cafe getCafe(){
        return cafe;
    }

    public void SetCafe(cafe cafe) {
        this.cafe = cafe;
    }

    public boolean islike() {return islike;}

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public LikeshopDTO(shoplike shoplike, cafe cafe, String path) {
        this.shoplike = shoplike;
        this.cafe = cafe;
        this.path = path;

    }


}
