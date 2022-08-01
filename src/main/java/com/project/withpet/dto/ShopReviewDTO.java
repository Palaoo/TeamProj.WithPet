package com.project.withpet.dto;

import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shopreview;


public class ShopReviewDTO {
    shopreview shopreview;
    cafe cafe;

    public com.project.withpet.domain.shopreview getShopreview() {
        return shopreview;
    }

    public void setShopreview(com.project.withpet.domain.shopreview shopreview) {
        this.shopreview = shopreview;
    }

    public com.project.withpet.domain.cafe getCafe() {
        return cafe;
    }

    public void setCafe(com.project.withpet.domain.cafe cafe) {
        this.cafe = cafe;
    }

    public ShopReviewDTO() {
    }

    public ShopReviewDTO(com.project.withpet.domain.shopreview shopreview, com.project.withpet.domain.cafe cafe) {
        this.shopreview = shopreview;
        this.cafe = cafe;
    }
}
