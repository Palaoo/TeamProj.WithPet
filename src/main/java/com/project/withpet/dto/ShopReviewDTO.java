package com.project.withpet.dto;

import com.project.withpet.domain.Shopreview;
import com.project.withpet.domain.cafe;


public class ShopReviewDTO {
    Shopreview shopreview;
    cafe cafe;
    String path;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Shopreview getShopreview() {
        return shopreview;
    }

    public void setShopreview(Shopreview shopreview) {
        this.shopreview = shopreview;
    }

    public cafe getCafe() {
        return cafe;
    }

    public void setCafe(cafe cafe) {
        this.cafe = cafe;
    }


    public ShopReviewDTO() {
    }

    public ShopReviewDTO(Shopreview shopreview, com.project.withpet.domain.cafe cafe, String path) {
        this.shopreview = shopreview;
        this.cafe = cafe;
        this.path = path;
    }

    public ShopReviewDTO(Shopreview shopreview, cafe cafe) {
        this.shopreview = shopreview;
        this.cafe = cafe;
    }
}
