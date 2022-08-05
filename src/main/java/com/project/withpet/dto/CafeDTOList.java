package com.project.withpet.dto;


import com.project.withpet.domain.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;

    private Long likeCount;

    public CafeDTOList(com.project.withpet.domain.cafe cafe, boolean islike, Long likeCount) {
        this.cafe = cafe;
        this.islike = islike;
        this.likeCount = likeCount;
    }

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
}
