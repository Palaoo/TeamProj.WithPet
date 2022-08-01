package com.project.withpet.dto;


import com.project.withpet.domain.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;

    public com.project.withpet.domain.cafe getCafe() {
        return cafe;
    }

    public void setCafe(cafe cafe) {
        this.cafe = cafe;
    }

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public CafeDTOList() {
    }

    public CafeDTOList(cafe cafe, boolean islike) {
        this.cafe = cafe;
        this.islike = islike;
    }
}
