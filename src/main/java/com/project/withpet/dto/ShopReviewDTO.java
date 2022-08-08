package com.project.withpet.dto;

import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shopreview;


public class ShopReviewDTO {
    shopreview shopreview;
    cafe cafe;
<<<<<<< HEAD


=======
    String path;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
    public shopreview getShopreview() {
        return shopreview;
    }

    public void setShopreview(shopreview shopreview) {
        this.shopreview = shopreview;
    }

    public cafe getCafe() {
        return cafe;
    }

    public void setCafe(cafe cafe) {
        this.cafe = cafe;
    }

<<<<<<< HEAD
    public ShopReviewDTO() {
    }


=======

    public ShopReviewDTO() {
    }

    public ShopReviewDTO(com.project.withpet.domain.shopreview shopreview, com.project.withpet.domain.cafe cafe, String path) {
        this.shopreview = shopreview;
        this.cafe = cafe;
        this.path = path;
    }
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6

    public ShopReviewDTO(shopreview shopreview, cafe cafe) {
        this.shopreview = shopreview;
        this.cafe = cafe;
    }
}
