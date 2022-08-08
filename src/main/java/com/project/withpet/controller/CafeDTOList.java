package com.project.withpet.controller;

import com.project.withpet.domain.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;
    String path;
    Double likeAvg;

    Long likeCount;

    public CafeDTOList(com.project.withpet.domain.cafe cafe, boolean islike, String path, Double likeAvg, Long likeCount) {
        this.cafe = cafe;
        this.islike = islike;
        this.path = path;
        this.likeAvg = likeAvg;
        this.likeCount = likeCount;
    }

    public CafeDTOList(cafe cafe, boolean shopLike, String path, Long likeCount) {
    }

    public double getLikeAvg() {
        return likeAvg;
    }

    public void setLikeAvg(double likeAvg) {
        this.likeAvg = likeAvg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public cafe getCafe() {
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

    public CafeDTOList(cafe cafe, boolean islike, String path) {
        this.cafe = cafe;
        this.islike = islike;
        this.path = path;
    }
}
