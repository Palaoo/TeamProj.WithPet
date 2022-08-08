package com.project.withpet.dto;


import com.project.withpet.domain.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;

    double likeAvg;

    private Long likeCount;

    public double getLikeAvg() {
        return likeAvg;
    }

    public void setLikeAvg(double likeAvg) {
        this.likeAvg = likeAvg;
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

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public CafeDTOList() {
    }

    public CafeDTOList(cafe cafe, boolean islike, Long likeCount) {
        this.cafe = cafe;
        this.islike = islike;
        this.likeCount = likeCount;
    }
}
